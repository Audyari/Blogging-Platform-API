package com.Blogging_Platform_API.Blogging_Platform_API.controller;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.Blogging_Platform_API.Blogging_Platform_API.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    // GET all blog posts
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllBlogPosts() {
        List<PostResponse> blogPosts = blogPostService.getAllBlogPosts();
        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }

    // GET single blog post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getBlogPostById(@PathVariable Long id) {
        Optional<PostResponse> blogPost = blogPostService.getBlogPostById(id);
        if (blogPost.isPresent()) {
            return new ResponseEntity<>(blogPost.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - Create a new blog post
    @PostMapping
    public ResponseEntity<PostResponse> createBlogPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse createdBlogPost = blogPostService.createBlogPost(postRequest);
        return new ResponseEntity<>(createdBlogPost, HttpStatus.CREATED);
    }

    // PUT - Update an existing blog post
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updateBlogPost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest) {
        try {
            PostResponse updatedBlogPost = blogPostService.updateBlogPost(id, postRequest);
            return new ResponseEntity<>(updatedBlogPost, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Delete a blog post
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBlogPost(@PathVariable Long id) {
        try {
            blogPostService.deleteBlogPost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}