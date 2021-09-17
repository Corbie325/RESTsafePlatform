package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.ConfidantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfidantRepository extends JpaRepository<ConfidantInfo, Long> {
}
