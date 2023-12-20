package io.joo.plus.postlike.repository;

import io.joo.plus.postlike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    PostLike findByPostIdAndMemberId(Long id, Long id1);

    Object countByPostIdAndIsLikeTrue(Long id);
}
