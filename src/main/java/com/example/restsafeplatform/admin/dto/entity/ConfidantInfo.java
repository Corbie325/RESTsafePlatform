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
@Table(name = "confidant_info")
public class ConfidantInfo {
    private static final String NOT_EMPTY = "Поле пустое";
    private static final String NOT_NULL = "Поле нулевое";
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conf_id_seq")
    @SequenceGenerator(name = "conf_id_seq", sequenceName = "conf_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long id;

    @ManyToOne
    private UserEntity userEntity;

    private String name;

    private String phone;

}
