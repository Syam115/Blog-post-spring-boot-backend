package com.example.blog_app.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private String author;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private List<CommentResponse> comments;

}
