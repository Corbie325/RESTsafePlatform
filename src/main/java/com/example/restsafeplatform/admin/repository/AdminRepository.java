package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UserEntity, Long> {
}
