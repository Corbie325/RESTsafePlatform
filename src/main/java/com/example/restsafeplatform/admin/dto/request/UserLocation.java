package com.example.restsafeplatform.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLocation {

    private Float lat;

    private Float lon;
}
