package com.back.domain.post.post.service;

import com.back.domain.post.post.document.Post;
import com.back.domain.post.post.repository.PostRepository;
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

    public Optional<Post> findById(String id) {
        return postRepository.findById(id);

        // 존재하지 않는 ID 조회 시 Optional.empty() 반환
    }
}
