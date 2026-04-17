package com.microService.rating.service.service.impl;

import com.microService.rating.service.entity.Ratings;
import com.microService.rating.service.exception.ResourceNotFoundException;
import com.microService.rating.service.repository.RatingRepository;
import com.microService.rating.service.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public Ratings createRating(Ratings ratings) {
        return ratingRepository.save(ratings);
    }

    @Override
    public List<Ratings> getAllRatings() {
        List<Ratings> ratings = ratingRepository.findAll();
        if(ratings.isEmpty()){
            throw new ResourceNotFoundException("No ratings found");
        }
        return ratings;
    }

    @Override
    public List<Ratings> getRatingsByHotelId(Long hotelId) {
        List<Ratings> ratings = ratingRepository.findByHotelId(hotelId);
        if(ratings.isEmpty()){
            throw new ResourceNotFoundException("No ratings found for hotel id: " + hotelId);
        }
        return ratings;
    }

    @Override
    public List<Ratings> getRatingsByUserId(Long userId) {
        List<Ratings> ratings = ratingRepository.findByUserId(userId);
        if(ratings.isEmpty()){
            throw new ResourceNotFoundException("No ratings found for user id: " + userId);
        }
        return ratings;
    }
}
