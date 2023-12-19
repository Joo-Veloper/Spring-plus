package io.joo.plus.Comment.entity;

import io.joo.plus.Comment.dto.CommentRequestDto;
import io.joo.plus.member.entity.Member;
import io.joo.plus.post.entity.Post;
import io.joo.plus.timestamped.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(CommentRequestDto requestDto, Member member, Post post){
        this.content = requestDto.getContent();
        this.member = member;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.content=requestDto.getContent();
    }
}
