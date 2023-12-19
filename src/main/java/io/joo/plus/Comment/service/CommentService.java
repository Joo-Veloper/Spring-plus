package io.joo.plus.Comment.service;

import io.joo.plus.Comment.dto.CommentRequestDto;
import io.joo.plus.Comment.dto.CommentResponseDto;
import io.joo.plus.Comment.entity.Comment;
import io.joo.plus.Comment.repostiory.CommentRepository;
import io.joo.plus.member.entity.Member;
import io.joo.plus.post.entity.Post;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public void createComment(Long postId, CommentRequestDto requestDto, MemberDetailsImpl memberDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = new Comment(requestDto, memberDetails.getMember(),post);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return commentRepository.findAllByPostId(post.getId()).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, CommentRequestDto requestDto, MemberDetailsImpl memberDetails) {
        Member member = memberDetails.getMember();
        Comment comment = validatePostCommentMember(postId,commentId,member);
        comment.update(requestDto);
    }

    public void deleteComment(Long postId, Long commentId, MemberDetailsImpl memberDetails) {
        Member member = memberDetails.getMember();
        Comment comment = validatePostCommentMember(postId,commentId,member);
        commentRepository.delete(comment);
    }


    private Comment validatePostCommentMember(Long postId, Long commentId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("게시글 내 해당 댓글이 존재하지 않습니다.");
        }
        if(!member.getId().equals(comment.getMember().getId())) {
            throw  new IllegalArgumentException("해당 개시글의 작성자가 아닙니다.");
        }

        return comment;
    }
}
