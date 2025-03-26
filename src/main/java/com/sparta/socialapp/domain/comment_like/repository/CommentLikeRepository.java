package com.sparta.socialapp.domain.comment_like.repository;

import com.sparta.socialapp.domain.comment_like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 댓글 ID와 유저 ID에 따른 좋아요
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);

    // 댓글 ID에 따른 좋아요 갯수
    @Query("SELECT COUNT(cl) FROM CommentLike cl where cl.comment.id = :commentId AND cl.likeStatus = 'LIKED'")
    Long countByCommentId(@Param("commentId") Long commentId);
}
