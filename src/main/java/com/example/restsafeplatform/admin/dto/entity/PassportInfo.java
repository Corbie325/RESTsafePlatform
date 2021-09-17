package com.example.restsafeplatform.admin.dto.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mobile_app")
public class PassportInfo {
    private static final String NOT_EMPTY = "Поле пустое";
    private static final String NOT_NULL = "Поле нулевое";
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pass_id_seq")
    @SequenceGenerator(name = "pass_id_seq", sequenceName = "pass_id_seq", schema = "mobile_app", allocationSize = 1)
    private Long id;


    @Id
    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 10, max = 11)
    private String passportNumberAndSerial;      // Номер паспорта

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    private UserEntity userEntity;


    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 50)
    private String registrationAddress; // Адрес регистрации


    @NotNull(message = NOT_NULL)
    @Past
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date passportDateOfIssue;   // Дата выдачи паспорта

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 50)
    private String passportPlaceOfIssue; // Место выдачи паспорта


}
