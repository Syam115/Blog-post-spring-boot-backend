package com.example.blog_app.controller;

import com.example.blog_app.dto.UserProfileRequest;
import com.example.blog_app.service.PostService;
import com.example.blog_app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProfileByUserId(id));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getPostsByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserProfileRequest user, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateProfileById(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        userService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }


}
