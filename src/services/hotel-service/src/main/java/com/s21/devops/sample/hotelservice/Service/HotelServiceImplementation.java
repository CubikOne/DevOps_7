package com.s21.devops.sample.hotelservice.Service;

import com.s21.devops.sample.hotelservice.Communication.CreateHotelReq;
import com.s21.devops.sample.hotelservice.Communication.HotelCapacityRes;
import com.s21.devops.sample.hotelservice.Communication.HotelInfoRes;
import com.s21.devops.sample.hotelservice.Exception.HotelAlreadyExistsException;
import com.s21.devops.sample.hotelservice.Exception.HotelNotFoundException;
import com.s21.devops.sample.hotelservice.Model.City;
import com.s21.devops.sample.hotelservice.Model.Country;
import com.s21.devops.sample.hotelservice.Model.Hotel;
import com.s21.devops.sample.hotelservice.Repository.CityRepository;
import com.s21.devops.sample.hotelservice.Repository.CountryRepository;
import com.s21.devops.sample.hotelservice.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImplementation implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Iterable<HotelInfoRes> getAllHotels() {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        List<HotelInfoRes> hotelInfoResList = new ArrayList<>();
        for (Hotel hotel:hotels) {
            hotelInfoResList.add(HotelInfoRes.hotelInfoResFromHotel(hotel));
        }
        return hotelInfoResList;
    }

    @Override
    public HotelInfoRes getHotel(UUID hotelUid) throws HotelNotFoundException {
        return HotelInfoRes.hotelInfoResFromHotel(
                hotelRepository.findByHotelUid(hotelUid).orElseThrow(() ->
                        new HotelNotFoundException("Hotel " + hotelUid + " was not found!")
                )
        );
    }

    @Override
    public UUID createHotel(CreateHotelReq createHotelReq) throws HotelAlreadyExistsException {
        Country country = countryRepository.findByCountry(createHotelReq.getLocation().getCountry()).orElse(new Country());
        if (country.getCountry() == null) {
            country.setCountry(createHotelReq.getLocation().getCountry());
        }

        City city = cityRepository.findByCityAndCountryCountry(
                createHotelReq.getLocation().getCity(),
                createHotelReq.getLocation().getCountry()).orElse(new City());
        if (city.getCity() == null) {
            city.setCity(createHotelReq.getLocation().getCity());
            city.setCountry(country);
        }

        Hotel hotel = hotelRepository.findByAddressAndCityCityAndCityCountryCountry(
                createHotelReq.getLocation().getAddress(),
                createHotelReq.getLocation().getCity(),
                createHotelReq.getLocation().getCountry())
                .orElse(new Hotel());

        if (hotel.getName() != null) {
            throw new HotelAlreadyExistsException("Hotel " + hotel.getName() + " in " + hotel.getCity().getCity() + " already exists!");
        }

        UUID hoteUid = UUID.randomUUID();
        hotel.setAddress(createHotelReq.getLocation().getAddress());
        hotel.setCity(city);
        hotel.setHotelUid(hoteUid);
        hotel.setName(createHotelReq.getName());
        hotel.setRooms(200);
        hotelRepository.save(hotel);
        return hoteUid;
    }

    @Override
    public HotelCapacityRes getHotelCapacity(UUID hotelUid) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findByHotelUid(hotelUid).orElseThrow(() ->
                new HotelNotFoundException("Hotel " + hotelUid + " was not found!")
        );
        HotelCapacityRes hotelCapacityRes = HotelCapacityRes.hotelCapacityResFromHotel(hotel);
        return hotelCapacityRes;
    }
}
