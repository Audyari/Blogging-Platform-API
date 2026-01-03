package com.Blogging_Platform_API.Blogging_Platform_API.repository;

import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<Post, Long> {
}