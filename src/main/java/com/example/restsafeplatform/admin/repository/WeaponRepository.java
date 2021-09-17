package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.WeaponInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponRepository extends JpaRepository<WeaponInfo, Long> {
    @Query(value =
            "SELECT * FROM mobile_app.weapon_info where user_entity_id = :id",
            nativeQuery = true)
    List<WeaponInfo> getAllWeaponByUser(Long id);

    @Query(value =
            "SELECT * FROM mobile_app.weapon_info WHERE weapon_info_id = :id",
            nativeQuery = true)
    Optional<WeaponInfo> findById(Long id);
}
