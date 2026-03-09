package com.example.blog_app.controller;

import com.example.blog_app.config.UserPrincipal;
import com.example.blog_app.dto.CommentRequest;
import com.example.blog_app.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentRequest comment,
                                           @PathVariable Long id,
                                           @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(comment, id, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long commentId, @AuthenticationPrincipal UserPrincipal user) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }

}
