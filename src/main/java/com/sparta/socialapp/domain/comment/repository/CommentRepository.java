package com.sparta.socialapp.domain.comment.repository;

import com.sparta.socialapp.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시글 아이디로 댓글 찾기
    List<Comment> findByBoardId(Long boardId);

    // 사용자 아이디로 댓글 찾기
    List<Comment> findByUserId(Long userId);
}
