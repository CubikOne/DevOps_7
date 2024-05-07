package com.s21.devops.sample.hotelservice.Repository;

import com.s21.devops.sample.hotelservice.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository  extends JpaRepository<Hotel, Long> {
    public Optional<Hotel> findByHotelUid(UUID uuid);
    public List<Hotel> findAll();
    public Optional<Hotel> findByAddressAndCityCityAndCityCountryCountry(String address, String cityCity, String CityCountryCountry);
}
