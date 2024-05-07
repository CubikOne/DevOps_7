package com.s21.devops.sample.sessionservice.Controller;

import com.s21.devops.sample.sessionservice.Communication.CreateUserReq;
import com.s21.devops.sample.sessionservice.Communication.UserUidRes;
import com.s21.devops.sample.sessionservice.Exception.CustomJwtException;
import com.s21.devops.sample.sessionservice.Exception.RoleNotFoundException;
import com.s21.devops.sample.sessionservice.Exception.UserAlreadyExistsException;
import com.s21.devops.sample.sessionservice.Service.SessionService;
import com.s21.devops.sample.sessionservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class SessionController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, EntityNotFoundException, RoleNotFoundException {
        String jwtToken = sessionService.authorize(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).header("Access-Control-Expose-Headers", "authorization").build();
    }

    @GetMapping("/validate")
    public UserUidRes validateToken(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, CustomJwtException {
        System.out.println("Validation");
        return sessionService.validate(authorization);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh (@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        String newToken = sessionService.refresh(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + newToken).build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserReq createUserReq)
            throws RoleNotFoundException, UserAlreadyExistsException {
        UUID uuid = userService.createUser(createUserReq.getUsername(), createUserReq.getPassword());
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userUid}")
                .buildAndExpand(uuid)
                .toUri()
        ).build();
    }
}
