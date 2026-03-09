package com.example.blog_app.dto;

import com.example.blog_app.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private String author;

    private String content;

    private LocalDateTime createdAt;

    private Long postId;

}
