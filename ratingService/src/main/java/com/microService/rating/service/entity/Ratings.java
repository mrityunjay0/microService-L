package com.microService.rating.service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ms_rating_table")
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ratingId;

    @Column(name = "HOTEL_ID", nullable = false)
    private Long hotelId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "FEEDBACK", length = 500)
    private String feedback;


    // CONSTRUCTORS, GETTERS, SETTERS

    public Ratings() {
    }

    public Ratings(Long ratingId, Long hotelId, Long userId, int rating, String feedback) {
        this.ratingId = ratingId;
        this.hotelId = hotelId;
        this.userId = userId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
