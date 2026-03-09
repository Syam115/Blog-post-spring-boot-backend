package com.example.blog_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Description should not be blank")
    private String description;

}
