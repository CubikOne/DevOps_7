package com.s21.devops.sample.hotelservice.Controller;

import com.s21.devops.sample.hotelservice.Communication.CreateHotelReq;
import com.s21.devops.sample.hotelservice.Communication.HotelCapacityRes;
import com.s21.devops.sample.hotelservice.Communication.HotelInfoRes;
import com.s21.devops.sample.hotelservice.Exception.HotelAlreadyExistsException;
import com.s21.devops.sample.hotelservice.Exception.HotelNotFoundException;
import com.s21.devops.sample.hotelservice.Service.HotelService;
import com.s21.devops.sample.hotelservice.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelsController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, EntityNotFoundException {
        String jwtToken = securityService.authorize(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).build();
    }

    @GetMapping("")
    public Iterable<HotelInfoRes> getHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{hotelUid}")
    public HotelInfoRes getHotelInfo(@PathVariable UUID hotelUid)
            throws HotelNotFoundException {
        return hotelService.getHotel(hotelUid);
    }

    @GetMapping("/{hotelUid}/rooms")
    public HotelCapacityRes getHotelCapacity(@PathVariable UUID hotelUid)
            throws HotelNotFoundException {
        return hotelService.getHotelCapacity(hotelUid);
    }

    @PostMapping("")
    public ResponseEntity<Void> addHotel(@Valid @RequestBody CreateHotelReq createHotelReq)
            throws HotelAlreadyExistsException {
        System.out.println("hotel was created");
        UUID hotelUid = hotelService.createHotel(createHotelReq);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{hotelUid}")
                .buildAndExpand(hotelUid)
                .toUri()
        ).build();
    }

    /*
    @PatchMapping("/{hotelUid}/rooms")
    public void patchRoomsInfo(@PathVariable UUID hotelUid, @Valid @RequestBody PatchRoomsInfoReq patchRoomsInfoReq) {
        System.out.println("rooms info for hotel " + hotelUid.toString() + "was changed");
    }*/
}
