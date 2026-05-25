package com.microService.user.service.services.impl;

import com.microService.user.service.entity.Hotel;
import com.microService.user.service.entity.Ratings;
import com.microService.user.service.entity.User;
import com.microService.user.service.exception.ResourceNotFoundException;
import com.microService.user.service.external.services.HotelService;
import com.microService.user.service.repository.UserRepository;
import com.microService.user.service.services.UserServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final HotelService hotelService;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, HotelService hotelService) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.hotelService = hotelService;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get all users with their ratings and hotel details
    @Override
    @CircuitBreaker(name = "getAllUsersCircuitBreaker", fallbackMethod = "getAllUsersFallback")
    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }

        // Fetch ratings for each user
        for (User user : users) {

            Ratings[] ratingsList;
            try{
                ratingsList = restTemplate.getForObject(
                        "http://RATINGSERVICE/ratings/user/" + user.getId(),
                        Ratings[].class );
            }
            catch (HttpClientErrorException.NotFound ex) {
                ratingsList = new Ratings[0]; // User has no ratings
            }

            if(ratingsList == null) {
                ratingsList = new Ratings[0]; // Handle null response from the Ratings Service
            }

            List<Ratings> ratings = Arrays.asList(ratingsList);

            // Fetch hotel details for each rating
            for (Ratings rating : ratings) {

                try {
                    Hotel hotel = hotelService.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);
                }
                catch (HttpClientErrorException.NotFound ex) {
                    rating.setHotel(null); // Hotel not found, set to null
                }
            }

            user.setRatings(ratings);
        }

        return users;
    }
    public List<User> getAllUsersFallback(Exception ex) {

        if(ex instanceof ResourceNotFoundException) {
            throw (ResourceNotFoundException) ex;
        }

        User fallbackUser = new User();

        fallbackUser.setId(0L);
        fallbackUser.setName("Fallback User");
        fallbackUser.setEmail("fallback@example.com");
        fallbackUser.setAbout("Service is temporarily unavailable");

        return List.of(fallbackUser);
    }


    // Get user by ID with their ratings and hotel details
    @Override
    @CircuitBreaker(name = "getUserByIdCircuitBreaker", fallbackMethod = "getUserByIdFallback")
    public User getUserById(Long userId) {

        // Fetch user from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Ratings[] ratingsList;

        try {
            // Fetch ratings from Rating Service
            ratingsList = restTemplate.getForObject(
                    "http://RATINGSERVICE/ratings/user/" + user.getId(),
                    Ratings[].class);
        }
        catch (HttpClientErrorException.NotFound e) {
            // User has no ratings
            ratingsList = new Ratings[0];
        }

        // Safety check
        if (ratingsList == null) {
            ratingsList = new Ratings[0];
        }

        List<Ratings> ratings = Arrays.asList(ratingsList);

        // Fetch hotel details for each rating
        for (Ratings rating : ratings) {
            try {
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
            }
            catch (HttpClientErrorException.NotFound e) {
                // Hotel not found
                rating.setHotel(null);
            }
        }
        user.setRatings(ratings);
        return user;
    }
    public User getUserByIdFallback(Long userId, Exception ex) {

        if(ex instanceof ResourceNotFoundException) {
            throw (ResourceNotFoundException) ex;
        }

        User fallbackUser = new User();

        fallbackUser.setId(userId);
        fallbackUser.setName("Fallback User");
        fallbackUser.setEmail("fallback@example.com");
        fallbackUser.setAbout("This is a fallback user due to service unavailability.");
        return fallbackUser;
    }

    @Override
    public User updateUser(User user, Long userId) {
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setAbout(user.getAbout());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
