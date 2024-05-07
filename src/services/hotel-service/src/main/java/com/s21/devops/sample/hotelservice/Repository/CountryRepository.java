package com.s21.devops.sample.hotelservice.Repository;

import com.s21.devops.sample.hotelservice.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    public Optional<Country> findByCountry(String country);
}
