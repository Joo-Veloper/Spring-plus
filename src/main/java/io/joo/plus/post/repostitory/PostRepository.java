package io.joo.plus.post.repostitory;

import io.joo.plus.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByMemberIdOrderByCreatedAtDesc(Long id, Pageable pageable);
}
