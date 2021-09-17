package com.example.restsafeplatform.admin.repository;

import com.example.restsafeplatform.admin.dto.entity.PassportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<PassportInfo, String> {

}
