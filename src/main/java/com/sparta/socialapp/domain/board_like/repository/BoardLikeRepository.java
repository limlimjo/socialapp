package com.sparta.socialapp.domain.board_like.repository;

import com.sparta.socialapp.domain.board_like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    // 게시글 ID와 유저 ID에 따른 좋아요
    Optional<BoardLike> findByBoardIdAndUserId(Long boardId, Long userId);

    // 게시글 ID에 따른 좋아요 갯수
    @Query("SELECT COUNT(bl) FROM BoardLike bl where bl.board.id = :boardId AND bl.likeStatus = 'LIKED'")
    Long countByBoardId(@Param("boardId") Long boardId);
}
