package com.back.domain.post.post.controller;

import com.back.BaseTest;
import com.back.domain.post.post.document.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class CommentControllerTests extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Post createTestPost() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType("application/json")
                                .content(
                                        objectMapper.writeValueAsBytes(
                                                Map.of(
                                                        "title", "Test Post Title",
                                                        "content", "Test Post Content",
                                                        "author", "Test Post Author"
                                                )
                                        )
                                )
                ).andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, Post.class);
    }

    // content 누락으로 인한 댓글 생성 실패
    @Test
    @DisplayName("POST /api/v1/posts/{postId}/comments - 실패 (content 누락)")
    void t1() throws Exception {
        Post post = createTestPost();
        mockMvc.perform(
                post("/api/v1/posts/{postId}/comments", post.getId())
                        .contentType("application/json")
                        .content(
                                objectMapper.writeValueAsBytes(
                                        Map.of(
                                                "author", "Test Author"
                                        )
                                )
                        )
        ).andExpect(status().isBadRequest());
    }

    // 존재하지 않는 id로 인한 댓글 실패
    @Test
    @DisplayName("POST /api/v1/posts/{postId}/comments - 실패 (존재하지 않는 postId)")
    void t2() throws Exception {
        mockMvc.perform(
                post("/api/v1/posts/{postId}/comments", "nonexistent-post-id")
                        .contentType("application/json")
                        .content(
                                objectMapper.writeValueAsBytes(
                                        Map.of(
                                                "content", "Test Content",
                                                "author", "Test Author"
                                        )
                                )
                        )
        ).andExpect(status().isNotFound());
    }

    // 댓글 생성 성공
    @Test
    @DisplayName("POST /api/v1/posts/{postId}/comments - 성공")
    void t3() throws Exception {
        Post post = createTestPost();
        mockMvc.perform(
                        post("/api/v1/posts/{postId}/comments", post.getId())
                                .contentType("application/json")
                                .content(
                                        objectMapper.writeValueAsBytes(
                                                Map.of(
                                                        "content", "Test Comment Content",
                                                        "author", "Test Comment Author"
                                                )
                                        )
                                )
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("content").value("Test Comment Content"))
                .andExpect(jsonPath("author").value("Test Comment Author"))
                .andExpect(jsonPath("postId").value(post.getId()))
                .andExpect(jsonPath("id").isNotEmpty());
    }
}
