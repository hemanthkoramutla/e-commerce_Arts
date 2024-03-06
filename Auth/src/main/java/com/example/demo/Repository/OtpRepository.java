package com.example.demo.Repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.OtpEntity;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByEmail(String email);
    void deleteByExpiryTimeBefore(LocalDateTime expiryTime);
}
