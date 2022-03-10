package com.example.userservice.domain.mail.repository;

import com.example.userservice.domain.mail.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-10.
 */
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByEmail(String email);
}
