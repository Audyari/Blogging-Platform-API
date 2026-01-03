package com.Blogging_Platform_API.Blogging_Platform_API.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts") // 1. Membuat table di database
public class Post {

    @Id // 2. Membuat primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 3. Membuat column di table
    private String title;

    @Lob // 4. Membuat column di table
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false) // 3. Membuat column di table
    private String author;

    @Column(name = "created_at", nullable = false, updatable = false) // 3. Membuat column di table
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // 3. Membuat column di table
    private LocalDateTime updatedAt;

    // Constructors
    public Post() {
    }

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}