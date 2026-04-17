package com.microService.rating.service.repository;

import com.microService.rating.service.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Ratings, Long> {

    // Custom finder method for finding ratings by hotelId
    List<Ratings> findByHotelId(Long hotelId);

    // Custom finder method for finding ratings by userId
    List<Ratings> findByUserId(Long userId);
}
