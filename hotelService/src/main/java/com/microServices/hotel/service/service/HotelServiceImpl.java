package com.microServices.hotel.service.service;

import com.microServices.hotel.service.entity.Hotel;
import com.microServices.hotel.service.exception.ResourceNotFoundException;
import com.microServices.hotel.service.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        return hotel;
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        if(hotels.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found");
        }
        return hotels;
    }

    @Override
    public Hotel updateHotel(Hotel updateHotel, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        hotel.setName(updateHotel.getName());
        hotel.setAbout(updateHotel.getAbout());
        hotel.setLocation(updateHotel.getLocation());
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        hotelRepository.delete(hotel);
    }
}
