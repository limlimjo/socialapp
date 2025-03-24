package com.sparta.socialapp.domain.board.repository;

import com.sparta.socialapp.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 사용자 아이디로 게시글 찾기
    List<Board> findByUserId(Long userId);
}
