package com.example.blog_app.service;

import com.example.blog_app.dto.UserProfileRequest;
import com.example.blog_app.dto.UserProfileResponse;
import com.example.blog_app.entity.User;
import com.example.blog_app.repository.PostRepository;
import com.example.blog_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse getProfileByUserId(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    public UserProfileResponse updateProfileById(UserProfileRequest userProfileRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userProfileRequest.getName());
        user.setEmail(userProfileRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userProfileRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public void deleteProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    private UserProfileResponse mapToResponse(User user) {
        Long postsCount = postRepository.countByUserId(user.getId());
        return UserProfileResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .postsCount(postsCount)
                .build();
    }

}
