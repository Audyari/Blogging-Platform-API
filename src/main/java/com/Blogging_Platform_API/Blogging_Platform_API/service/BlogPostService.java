package com.Blogging_Platform_API.Blogging_Platform_API.service;

import com.Blogging_Platform_API.Blogging_Platform_API.model.BlogPost;
import com.Blogging_Platform_API.Blogging_Platform_API.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    
    @Autowired
    private BlogPostRepository blogPostRepository;
    
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }
    
    public Optional<BlogPost> getBlogPostById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        return blogPostRepository.findById(id);
    }

    /**
     * Creates a new blog post.
     * @param blogPost the blog post to create
     * @return the created blog post (not null)
     */
    public BlogPost createBlogPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    /**
     * Updates an existing blog post.
     * @param id the ID of the blog post to update
     * @param blogPostDetails the updated blog post details
     * @return the updated blog post (not null)
     */
    public BlogPost updateBlogPost(Long id, BlogPost blogPostDetails) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        blogPost.setTitle(blogPostDetails.getTitle());
        blogPost.setContent(blogPostDetails.getContent());
        blogPost.setAuthor(blogPostDetails.getAuthor());
        // createdAt tidak diupdate karena merupakan field yang hanya di-set saat pembuatan

        return blogPostRepository.save(blogPost);
    }

    public void deleteBlogPost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Blog post ID cannot be null");
        }
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        blogPostRepository.delete(blogPost);
    }
}