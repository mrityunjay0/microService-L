package com.microService.rating.service.controller;

import com.microService.rating.service.entity.Ratings;
import com.microService.rating.service.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // create rating
    @PostMapping
    public ResponseEntity<Ratings> createRating(@RequestBody Ratings ratings) {
        Ratings createdRating = ratingService.createRating(ratings);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }


    // get all ratings
    @GetMapping
    public ResponseEntity<List<Ratings>> getAllRatings() {
        List<Ratings> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }


    // get ratings by hotel id
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Ratings>> getRatingsByHotelId(@PathVariable Long hotelId) {
        List<Ratings> ratings = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.ok(ratings);
    }


    // get ratings by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ratings>> getRatingsByUserId(@PathVariable Long userId) {
        List<Ratings> ratings = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.ok(ratings);
    }
}
