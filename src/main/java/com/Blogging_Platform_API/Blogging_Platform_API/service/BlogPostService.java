package com.Blogging_Platform_API.Blogging_Platform_API.service;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import com.Blogging_Platform_API.Blogging_Platform_API.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. Ambil Data -> Ubah -> Simpan -> Kembalikan.

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<PostResponse> getAllBlogPosts() {
        List<Post> posts = blogPostRepository.findAll();
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<PostResponse> getBlogPostById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        Optional<Post> post = blogPostRepository.findById(id);
        return post.map(this::convertToResponseDto);
    }

    /**
     * Creates a new blog post.
     * 
     * @param postRequest the blog post request DTO
     * @return the created blog post response DTO (not null)
     */
    public PostResponse createBlogPost(PostRequest postRequest) {
        Post post = convertToEntity(postRequest);
        Post savedPost = blogPostRepository.save(post); // Simpan ke database
        return convertToResponseDto(savedPost);
    }

    /**
     * Updates an existing blog post.
     * 
     * @param id          the ID of the blog post to update
     * @param postRequest the updated blog post details
     * @return the updated blog post response DTO (not null)
     */
    public PostResponse updateBlogPost(Long id, PostRequest postRequest) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        Post post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(postRequest.getCategory());
        post.setTags(convertTagsToString(postRequest.getTags()));
        // createdAt tidak diupdate karena merupakan field yang hanya di-set saat
        // pembuatan

        Post updatedPost = blogPostRepository.save(post); // Simpan ke database
        return convertToResponseDto(updatedPost);
    }

    /**
     * Deletes an existing blog post.
     * 
     * @param id the ID of the blog post to delete
     */
    public void deleteBlogPost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        Post post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        blogPostRepository.delete(post); // Hapus dari database
    }

    /**
     * Helper methods to convert between entities and DTOs
     */
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
            return objectMapper.readValue(tagsString, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting string to tags", e);
        }
    }
}