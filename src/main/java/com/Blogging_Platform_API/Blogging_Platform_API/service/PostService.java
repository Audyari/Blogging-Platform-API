package com.Blogging_Platform_API.Blogging_Platform_API.service;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import com.Blogging_Platform_API.Blogging_Platform_API.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<PostResponse> getPostById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        Optional<Post> post = postRepository.findById(id);
        return post.map(this::convertToResponseDto);
    }

    public PostResponse createPost(PostRequest postRequest) {
        Post post = convertToEntity(postRequest);
        Post savedPost = postRepository.save(post);
        return convertToResponseDto(savedPost);
    }

    public PostResponse updatePost(Long id, PostRequest postRequest) {
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());

        Post updatedPost = postRepository.save(post);
        return convertToResponseDto(updatedPost);
    }

    public void deletePost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        postRepository.delete(post);
    }

    // Helper methods to convert between entities and DTOs
    private Post convertToEntity(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        return post;
    }

    private PostResponse convertToResponseDto(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthor(post.getAuthor());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        return response;
    }
}