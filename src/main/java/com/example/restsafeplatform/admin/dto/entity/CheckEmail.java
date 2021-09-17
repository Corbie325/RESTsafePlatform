package com.example.restsafeplatform.admin.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "check_email")
public class CheckEmail {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_id_seq")
    @SequenceGenerator(name = "check_id_seq", sequenceName = "check_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long id;
    private Long userId;
    private String token;
}
