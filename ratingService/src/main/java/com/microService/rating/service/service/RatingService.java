package com.microService.rating.service.service;

import com.microService.rating.service.entity.Ratings;

import java.util.List;

public interface RatingService {

    // create ratings
    Ratings createRating(Ratings ratings);

    // get all ratings
    List<Ratings> getAllRatings();

    // get all ratings by hotel id
    List<Ratings> getRatingsByHotelId(Long hotelId);

    // get all ratings by user id
    List<Ratings> getRatingsByUserId(Long userId);
}
