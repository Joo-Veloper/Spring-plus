package io.joo.plus.post.entity;

import io.joo.plus.member.entity.Member;
import io.joo.plus.post.dto.PostRequestDto;
import io.joo.plus.timestamped.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Post(PostRequestDto requestDto, Member member){
        this.member = member;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(PostRequestDto requestDto) {
        this.title=requestDto.getTitle();
        this.content= requestDto.getContent();
    }
}
