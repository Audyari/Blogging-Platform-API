package com.Blogging_Platform_API.Blogging_Platform_API.service;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import com.Blogging_Platform_API.Blogging_Platform_API.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    //1. "Otak" Penyimpanan Data (Dependency Injection)
    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<PostResponse> getAllPosts() {
        // 3.1. Ambil Data
        List<Post> posts = postRepository.findAll(); 
        // 3.2. Ubah Data
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<PostResponse> getPostById(Long id) {
        // 3.1. Ambil Data
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        Optional<Post> post = postRepository.findById(id);
        // 3.2. Ubah Data
        return post.map(this::convertToResponseDto);
    }

    public PostResponse createPost(PostRequest postRequest) {
        // 3.1. Ambil Data
        Post post = convertToEntity(postRequest);
        // 3.2. Ubah Data
        Post savedPost = postRepository.save(post);
        // 3.3. Kembalikan Data
        return convertToResponseDto(savedPost);
    }   

    public PostResponse updatePost(Long id, PostRequest postRequest) {
        // 3.1. Ambil Data
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(postRequest.getCategory());
        post.setTags(convertTagsToString(postRequest.getTags()));

        Post updatedPost = postRepository.save(post);
        // 3.3. Kembalikan Data
        return convertToResponseDto(updatedPost);
    }

    public void deletePost(Long id) {
        // 3.1. Ambil Data
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
        post.setCategory(postRequest.getCategory());
        post.setTags(convertTagsToString(postRequest.getTags()));
        return post;
    }

    private PostResponse convertToResponseDto(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCategory(post.getCategory());
        response.setTags(convertStringToTags(post.getTags()));
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        return response;
    }

    private String convertTagsToString(List<String> tags) {
        // 3.1. Ambil Data
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tags to string", e);
        }
    }

    private List<String> convertStringToTags(String tagsString) {
        if (tagsString == null || tagsString.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(tagsString, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting string to tags", e);
        }
    }
}

//postRepository.findAll();    // Ambil semua
//postRepository.findById(id); // Cari satu
//postRepository.save(post);   // Simpan (baik baru maupun update)
//postRepository.delete(post); // Hapus
