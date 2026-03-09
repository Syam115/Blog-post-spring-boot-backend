package com.example.blog_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Provide valid email format")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 4, message = "Password size should be at least 4")
    private String password;

}
