package com.Blogging_Platform_API.Blogging_Platform_API;

import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostRequest;
import com.Blogging_Platform_API.Blogging_Platform_API.dto.PostResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Blogging_Platform_API.Blogging_Platform_API.service.PostService;
import com.Blogging_Platform_API.Blogging_Platform_API.controller.V2PostController;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(V2PostController.class)
public class V2PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePostSuccess() throws Exception {
        // Arrange
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("My First Blog Post");
        postRequest.setContent("This is the content of my first blog post.");
        postRequest.setCategory("Technology");
        postRequest.setTags(Arrays.asList("Tech", "Programming"));

        PostResponse expectedResponse = new PostResponse();
        expectedResponse.setId(1L);
        expectedResponse.setTitle("My First Blog Post");
        expectedResponse.setContent("This is the content of my first blog post.");
        expectedResponse.setCategory("Technology");
        expectedResponse.setTags(Arrays.asList("Tech", "Programming"));
        // Note: createdAt and updatedAt would be set by the service

        when(postService.createPost(any(PostRequest.class))).thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v2/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("My First Blog Post"))
                .andExpect(jsonPath("$.content").value("This is the content of my first blog post."))
                .andExpect(jsonPath("$.category").value("Technology"))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags[0]").value("Tech"))
                .andExpect(jsonPath("$.tags[1]").value("Programming"));
    }

    @Test
    public void testCreatePostValidationError() throws Exception {
        // Arrange - Invalid request (missing title)
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle(""); // Invalid - blank title
        postRequest.setContent("This is the content of my first blog post.");

        // Act & Assert
        mockMvc.perform(post("/api/v2/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isBadRequest());
    }
}