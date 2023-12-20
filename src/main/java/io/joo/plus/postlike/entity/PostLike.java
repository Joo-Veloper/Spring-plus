package io.joo.plus.postlike.entity;

import io.joo.plus.member.entity.Member;
import io.joo.plus.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_like")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column
    private Boolean isLike = false;


    public PostLike(Post post, Member member, boolean b){
        this.post = post;
        this.member = member;
        this.isLike = b;
    }
    public void setIsLike(Boolean trueOrFalse) {
        this.isLike = trueOrFalse;
    }

}
