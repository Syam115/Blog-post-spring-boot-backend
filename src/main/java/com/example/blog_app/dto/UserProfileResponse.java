package com.example.blog_app.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {

    private String name;

    private String email;

    private Long postsCount;

}
