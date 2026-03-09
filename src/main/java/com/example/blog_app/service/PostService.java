package com.example.blog_app.service;

import com.example.blog_app.dto.CommentResponse;
import com.example.blog_app.dto.PostRequest;
import com.example.blog_app.dto.PostResponse;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostResponse createPost(PostRequest postRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post newPost = Post
                .builder()
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Post savedPost = postRepository.save(newPost);

        return mapToResponse(savedPost);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToResponse(post);
    }

    public List<PostResponse> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PostResponse updatePost(PostRequest postRequest, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this post");
        }

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this post");
        }
        postRepository.delete(post);
    }

    private PostResponse mapToResponse(Post post) {
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        String name = post.getUser().getName();
        List<CommentResponse> commentResponses = comments
                .stream()
                .map(this::mapToResponse)
                .toList();

        return PostResponse
                .builder()
                .author(name)
                .title(post.getTitle())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .comments(commentResponses)
                .build();
    }

    private CommentResponse mapToResponse(Comment comment) {
        String name = comment.getUser().getName();
        return CommentResponse
                .builder()
                .author(name)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .build();
    }


}
