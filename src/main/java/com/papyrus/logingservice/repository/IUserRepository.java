package com.papyrus.logingservice.repository;

import com.papyrus.logingservice.documents.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
