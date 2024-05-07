package com.s21.devops.sample.hotelservice.Repository;

import com.s21.devops.sample.hotelservice.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    public Optional<City> findByCityAndCountryCountry(String city, String countryCountry);
}
