package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.Unit;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> getUnitBySerial(String serial);
    @Query(value =
           "SELECT * FROM mobile_app.unit where user_entity_id = :id",
            nativeQuery = true)
    List<Unit> getUnitByUserEntityId(Long id);
}
