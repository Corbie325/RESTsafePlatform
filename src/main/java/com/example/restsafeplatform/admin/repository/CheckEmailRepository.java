package com.example.restsafeplatform.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restsafeplatform.admin.dto.entity.CheckEmail;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CheckEmailRepository extends JpaRepository<CheckEmail, Long> {

    @Query(value =
            "SELECT * FROM mobile_app.check_email where user_id = :userId",
            nativeQuery = true)
    Optional<CheckEmail> findByUserId(Long userId);
}
