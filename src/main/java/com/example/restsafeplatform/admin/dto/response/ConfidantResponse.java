package com.example.restsafeplatform.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfidantResponse {
    private Long id;

    private Long userId;

    private String name;

    private String phone;
}
