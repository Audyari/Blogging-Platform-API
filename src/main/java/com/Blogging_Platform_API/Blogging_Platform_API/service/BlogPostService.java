package com.Blogging_Platform_API.Blogging_Platform_API.service;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import com.Blogging_Platform_API.Blogging_Platform_API.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

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
     * @param postRequest the blog post request DTO
     * @return the created blog post response DTO (not null)
     */
    public PostResponse createBlogPost(PostRequest postRequest) {
        Post post = convertToEntity(postRequest);
        Post savedPost = blogPostRepository.save(post);
        return convertToResponseDto(savedPost);
    }

    /**
     * Updates an existing blog post.
     * @param id the ID of the blog post to update
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
        post.setAuthor(postRequest.getAuthor());
        // createdAt tidak diupdate karena merupakan field yang hanya di-set saat pembuatan

        Post updatedPost = blogPostRepository.save(post);
        return convertToResponseDto(updatedPost);
    }

    public void deleteBlogPost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        Post post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        blogPostRepository.delete(post);
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