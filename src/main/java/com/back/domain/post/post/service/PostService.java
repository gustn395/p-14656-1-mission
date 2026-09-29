package com.back.domain.post.post.service;

import com.back.domain.post.post.document.Post;
import com.back.domain.post.post.repository.PostRepository;
import com.back.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count(){
        return postRepository.count();
    }

    // JPA: save() 후 영속성 컨텍스트에서 관리되며, 같은 트랜잭션 내에서 변경 감지(Dirty Checking)로 자동 저장
    // Elasticsearch: 영속성 컨텍스트가 없으므로 변경 시마다 save() 호출 필수
    public Post create(String title, String content, String author) {
        Post post = new Post(title, content, author);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(()->new NotFoundException("Post not found with id: " + id));
    }

    public Post update(String id, String title, String content) {
        Post post = findById(id);

        if (title != null){
            post.setTitle(title);
        }

        if (content != null){
            post.setContent(content);
        }

        return postRepository.save(post);
    }

    public void delete(String id) {
        Post post = findById(id);

        postRepository.delete(post);
    }
}
