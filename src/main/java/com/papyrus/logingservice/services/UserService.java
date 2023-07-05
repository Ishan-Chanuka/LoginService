package com.papyrus.logingservice.services;

import com.papyrus.logingservice.config.JWTService;
import com.papyrus.logingservice.documents.User;
import com.papyrus.logingservice.models.AuthenticationResponse;
import com.papyrus.logingservice.models.LogInRequest;
import com.papyrus.logingservice.models.SignInRequest;
import com.papyrus.logingservice.repository.IUserRepository;
import com.papyrus.logingservice.roles.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    private final IUserRepository _userService;
    private final PasswordEncoder _encoder;
    private final JWTService _jwtService;
    private final AuthenticationManager _authManager;

    public UserService(IUserRepository userService, PasswordEncoder encoder, JWTService jwtService, AuthenticationManager authManager) {
        _userService = userService;
        _encoder = encoder;
        _jwtService = jwtService;
        _authManager = authManager;
    }

    @Override
    public AuthenticationResponse signUp(SignInRequest request){
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(_encoder.encode(request.getPassword()))
                .role(Roles.EMPLOYEE)
                .build();

        _userService.save(user);

        var jwt = _jwtService.generateToken(user);
        var response = AuthenticationResponse.builder().token(jwt).build();

        return response;
    }

    @Override
    public AuthenticationResponse logIn(LogInRequest request) {
        _authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = _userService.findByEmail(request.getEmail()).orElseThrow();
        var jwt = _jwtService.generateToken(user);
        var response = AuthenticationResponse.builder().token(jwt).build();

        return response;
    }
}
