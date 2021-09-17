package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.TtxInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TtxRepository extends JpaRepository<TtxInfo, Long> {
}
