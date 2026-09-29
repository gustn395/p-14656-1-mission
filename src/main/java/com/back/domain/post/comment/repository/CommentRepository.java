package com.back.domain.post.comment.repository;

import com.back.domain.post.comment.document.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CommentRepository extends ElasticsearchRepository<Comment,String> {
    List<Comment> findAll();
    List<Comment> findByPostId(String postId);

    Page<Comment> findByPostId(String postId, Pageable pageable);

}
