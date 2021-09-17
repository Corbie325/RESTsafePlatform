package com.example.restsafeplatform.admin.dto.data;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
public class WeaponData {

    private String passport_id;

    private String name;

    private String type;    // Тип

    private String place;  //место покупки

    private String model;   // Модель

    private String caliber; // Калибр

    private String manufacture;

    private String gost;  //ГОСТ

    private String weight; // вес

    private String dimensions;  // габариты

    private String extraInfo;   //доп инфа

    private Long capacity; // емкость магазина

}
