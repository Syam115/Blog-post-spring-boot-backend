package com.example.blog_app.controller;

import com.example.blog_app.config.UserPrincipal;
import com.example.blog_app.dto.CommentRequest;
import com.example.blog_app.dto.PostRequest;
import com.example.blog_app.service.CommentService;
import com.example.blog_app.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest post,
                                        @AuthenticationPrincipal UserPrincipal user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post, user.getId()));
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPosts(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUserId(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostRequest post, @PathVariable("id") Long postId,
                                        @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(post, postId, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId,
                                        @AuthenticationPrincipal UserPrincipal user) {
        postService.deletePost(postId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest comment,
                                           @PathVariable("id") Long postId,
                                           @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment, postId, user.getId()));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentByPostId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(id));
    }

}
