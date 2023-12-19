package io.joo.plus.member.entity;

import io.joo.plus.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Setter
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")  //LAZY 주의
    private List<Post> postList = new ArrayList<>();


    public Member(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}
