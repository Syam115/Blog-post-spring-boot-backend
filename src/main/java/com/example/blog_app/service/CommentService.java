package com.example.blog_app.service;

import com.example.blog_app.dto.CommentRequest;
import com.example.blog_app.dto.CommentResponse;
import com.example.blog_app.entity.Comment;
import com.example.blog_app.entity.Post;
import com.example.blog_app.entity.User;
import com.example.blog_app.repository.CommentRepository;
import com.example.blog_app.repository.PostRepository;
import com.example.blog_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponse createComment(CommentRequest commentRequest, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment
                .builder()
                .content(commentRequest.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    public List<CommentResponse> getCommentsByPostId(Long id) {
        List<Comment> comments = commentRepository.findByPostId(id);
        return comments
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CommentResponse updateComment(CommentRequest commentRequest, Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner for this comment");
        }

        comment.setContent(commentRequest.getContent());

        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner for this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {
        String name = comment.getUser().getName();
        Post post = comment.getPost();
        return CommentResponse
                .builder()
                .content(comment.getContent())
                .author(name)
                .createdAt(comment.getCreatedAt())
                .postId(post.getId())
                .build();
    }

}
