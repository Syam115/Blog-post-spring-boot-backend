package com.example.blog_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    @NotBlank(message = "Comment should not be blank")
    private String content;

}
