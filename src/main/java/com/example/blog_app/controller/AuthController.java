package com.example.blog_app.controller;

import com.example.blog_app.config.UserPrincipal;
import com.example.blog_app.dto.LoginRequest;
import com.example.blog_app.dto.SignupRequest;
import com.example.blog_app.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> singUp(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.profile(user.getId()));
    }

}
