package com.example.restsafeplatform.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionRequest {

    private String dcQuestion;

    private String dcAnswer;

}
