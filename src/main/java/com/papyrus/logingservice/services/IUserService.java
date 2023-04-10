package com.papyrus.logingservice.services;

import com.papyrus.logingservice.documents.User;
import com.papyrus.logingservice.models.AuthenticationResponse;
import com.papyrus.logingservice.models.LogInRequest;
import com.papyrus.logingservice.models.SignInRequest;

public interface IUserService {
    AuthenticationResponse signUp(SignInRequest entity);
    AuthenticationResponse logIn(LogInRequest entity);
}
