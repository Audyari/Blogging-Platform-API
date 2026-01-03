package com.Blogging_Platform_API.Blogging_Platform_API.controller;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * API Version 2 Controller
 * Provides enhanced blog post functionality with improved validation and response handling
 */
@RestController
@RequestMapping("/api/v2/posts")
@CrossOrigin(origins = "*")
public class V2PostController {

    @Autowired
    private PostService postService;

    /**
     * Create a new blog post
     *
     * POST /posts
     * {
     *   "title": "My First Blog Post",
     *   "content": "This is the content of my first blog post.",
     *   "category": "Technology",
     *   "tags": ["Tech", "Programming"]
     * }
     *
     * Returns 201 Created with the created post or 400 Bad Request with error message
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse createdPost = postService.createPost(postRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Additional endpoints can be added here for API v2 as needed
    // For now, we're focusing on the create post endpoint as requested

    // GET all posts
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // GET single post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Optional<PostResponse> post = postService.getPostById(id);
        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}