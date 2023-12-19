package io.joo.plus.post.repostitory;

import io.joo.plus.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop10ByOrderByCreatedAtDesc();

    List<Post> findAllByMemberIdOrderByCreatedAtDesc(Long id);
}
