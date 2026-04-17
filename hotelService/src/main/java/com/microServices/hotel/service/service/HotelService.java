package com.microServices.hotel.service.service;

import com.microServices.hotel.service.entity.Hotel;

import java.util.List;

public interface HotelService {

    // create hotel
    public Hotel createHotel(Hotel hotel);

    // get hotel by id
    public Hotel getHotelById(Long hotelId);

    // get all hotels
    public List<Hotel> getAllHotels();

    // update hotel
    public Hotel updateHotel(Hotel hotel, Long hotelId);

    // delete hotel
    public void deleteHotel(Long hotelId);

}
