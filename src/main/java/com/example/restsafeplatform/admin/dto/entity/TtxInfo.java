package com.example.restsafeplatform.admin.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "performance_characteristics")
public class TtxInfo {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ttx_id_seq")
    @SequenceGenerator(name = "ttx_id_seq", sequenceName = "ttx_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long id;

    private String caliber;

    private String manufacture;

    private String gost;  //ГОСТ

    private String weight; // вес

    private String dimensions;  // габариты

    private String extraInfo;   //доп инфа

    private Long capacity; // емкость магазина

    //TODO сделать нормальную связь
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ttxInfo")
    @JsonIgnore
    private WeaponInfo weaponInfo;


}
