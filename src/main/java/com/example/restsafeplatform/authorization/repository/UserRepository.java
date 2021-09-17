package com.example.restsafeplatform.authorization.repository;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>,  JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    @Query(value =
            "SELECT * FROM mobile_app.user_entity t WHERE t.id = :id",
            nativeQuery = true)
    Optional<UserEntity> findById(Long id);

    @Query(value =
            "SELECT * FROM mobile_app.user_entity WHERE passport_id = :SerialAndNumber",
            nativeQuery = true)
    Optional<UserEntity> getUserByPassport(String SerialAndNumber);


}
