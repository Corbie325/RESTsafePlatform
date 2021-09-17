package com.example.restsafeplatform.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionResponse {

    private String dcQuestion;

    private String dcAnswer;

}
