package com.papyrus.logingservice.controller;

import com.papyrus.logingservice.documents.User;
import com.papyrus.logingservice.models.AuthenticationResponse;
import com.papyrus.logingservice.models.LogInRequest;
import com.papyrus.logingservice.models.SignInRequest;
import com.papyrus.logingservice.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")
public class AuthController {

    final IUserService _userService;

    public AuthController(IUserService userService) {
        _userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignInRequest entity){
        return ResponseEntity.ok(_userService.signUp(entity));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody LogInRequest entity){
        return ResponseEntity.ok(_userService.logIn(entity));
    }
}
