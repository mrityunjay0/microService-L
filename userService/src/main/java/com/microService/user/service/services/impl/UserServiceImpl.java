package com.microService.user.service.services.impl;

import com.microService.user.service.entity.Hotel;
import com.microService.user.service.entity.Ratings;
import com.microService.user.service.entity.User;
import com.microService.user.service.exception.ResourceNotFoundException;
import com.microService.user.service.external.services.HotelService;
import com.microService.user.service.repository.UserRepository;
import com.microService.user.service.services.UserServices;
import org.springframework.stereotype.Service;
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

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }

        // Fetch ratings for each user from the Ratings Service
        for (User user : users) {
            // Fetch ratings for the user from the Ratings Service
            Ratings[] ratingsList;
            try {
                ratingsList = restTemplate.getForObject(
                        "http://RATINGSERVICE/ratings/user/" + user.getId(), Ratings[].class);
            }
            catch (Exception e) {
                ratingsList = new Ratings[0]; // If there's an error fetching ratings, set it to an empty array
            }

            List<Ratings> ratings = Arrays.asList(ratingsList);

            // set Hotel details for each rating
            for (Ratings rating : ratings) {
                try{
                    Hotel hotel = hotelService.getHotel(rating.getHotelId());

                    rating.setHotel(hotel);
                }
                catch (Exception e) {
                    rating.setHotel(null); // If there's an error fetching hotel details, set it to null
                }
            }

            user.setRatings(ratings);
        }

        return users;
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Fetch ratings for the user from the Ratings Service
        Ratings[] ratingsList;
        try {
            ratingsList = restTemplate.getForObject(
                    "http://RATINGSERVICE/ratings/user/" + user.getId(), Ratings[].class);
        }
        catch (Exception e) {
            ratingsList = new Ratings[0]; // If there's an error fetching ratings, set it to an empty array
        }

        List<Ratings> ratings = Arrays.asList(ratingsList);

        // set Hotel details for each rating
        for (Ratings rating : ratings) {
            try{
                Hotel hotel = restTemplate.getForObject(
                        "http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);

                rating.setHotel(hotel);
            }
            catch (Exception e) {
                rating.setHotel(null); // If there's an error fetching hotel details, set it to null
            }
        }

        user.setRatings(ratings);
        return user;
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
