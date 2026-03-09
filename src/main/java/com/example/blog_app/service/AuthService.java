package com.example.blog_app.service;

import com.example.blog_app.config.JwtUtil;
import com.example.blog_app.config.UserPrincipal;
import com.example.blog_app.dto.LoginRequest;
import com.example.blog_app.dto.SignupRequest;
import com.example.blog_app.dto.UserProfileResponse;
import com.example.blog_app.entity.User;
import com.example.blog_app.repository.PostRepository;
import com.example.blog_app.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String register(@Valid SignupRequest request) {
        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if (userExists) {
            return "User already exists";
        }

        User user = User
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return "Account created successfully";
    }


    public String login(@Valid LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(request.getEmail());
        }

        return "Login Fail";
    }

    public UserProfileResponse profile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long postsCount = postRepository.countByUserId(id);

        return UserProfileResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .postsCount(postsCount)
                .build();
    }
}
