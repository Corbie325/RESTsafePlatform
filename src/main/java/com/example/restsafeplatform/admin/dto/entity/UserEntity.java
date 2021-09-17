package com.example.restsafeplatform.admin.dto.entity;


import com.example.restsafeplatform.authorization.dto.model.Role;
import com.example.restsafeplatform.authorization.dto.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(schema = "mobile_app")
public class UserEntity {
    private static final String NOT_EMPTY = "Поле пустое";
    private static final String NOT_NULL = "Поле нулевое";
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", schema = "mobile_app", allocationSize = 1)

    @Id
    private Long id;

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 100)
    @Email(message = "Email должен быть валидным")
    private String email;

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 100)
    private String password;

    private String fastCode; //4-х значный пароль

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 100)
    private String lastName;    // Фамилия

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 100)
    private String firstName;   // Имя

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 100)
    private String middleName;  // Отчество

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 1, max = 20)
    private String fio;         // ФИО сокращенно

    @NotNull(message = NOT_NULL)
    @Past(message = "Неверное время")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date birthDate;     // Дата рождения

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "hash_url")
    private String hashURL;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    //@Pattern(regexp = "[0-9]")
    // /d/d/d/d/d/d/d/d/d/d/d 89635037695
    //@Pattern(regexp = "\d\d\d\d\d\d\d\d\d\d\d")
    @Size(min = 11, max = 12)
    private String contactPhone;     // Телефон 1

    private String contactPhone1;     // Телефон 2

    private String contactPhone2;     // Телефон 3

    private String dcQuestion;        // кодовый вопрос

    private String dcAnswer;        // кодовый ответ

    private Integer email_confirmed = 0;

    private Integer pass_confirmed = 0;

    private float lat;

    private float lon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id")
    @JsonIgnore
    PassportInfo passport;

}
