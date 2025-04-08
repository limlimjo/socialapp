package com.sparta.socialapp.common.logger.service;

import com.sparta.socialapp.common.logger.EventLogFactory;
import com.sparta.socialapp.common.logger.EventLogger;
import com.sparta.socialapp.common.logger.dto.EventLog;
import com.sparta.socialapp.common.util.LogRequestUtils;
import com.sparta.socialapp.common.util.SecurityUtils;
import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.comment.dto.CommentResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventLogFactory eventLogFactory;
    private final EventLogger eventLogger;

    // 게시글 상세페이지 클릭했을 때
    public void logClickBoardDetailPage(Board board, HttpServletRequest request) {

        Map<String, Object> info = new HashMap<>();
        info.put("title", board.getTitle());
        info.put("content", board.getContent());

        EventLog eventLog = eventLogFactory.createEventLog(
                "click_board_detail_page",
                SecurityUtils.getCurrentUserEmailOrGuest(),
                LogRequestUtils.getClientIp(request),
                LogRequestUtils.getDevice(request),
                LogRequestUtils.getPageUrl(request),
                info
        );

        eventLogger.logClickEvent(eventLog);
    }

    // 게시글 댓글 클릭했을 때
    public void logClickBoardComment(Board board, List<CommentResponseDto> commentList, HttpServletRequest request) {

        Map<String, Object> info = new HashMap<>();
        info.put("title", board.getTitle());
        info.put("commentList", commentList);

        EventLog eventLog = eventLogFactory.createEventLog(
                "click_board_comment",
                SecurityUtils.getCurrentUserEmailOrGuest(),
                LogRequestUtils.getClientIp(request),
                LogRequestUtils.getDevice(request),
                LogRequestUtils.getPageUrl(request),
                info
        );

        eventLogger.logClickEvent(eventLog);
    }

    // 게시글 좋아요 했을 때
    public void logWishBoardLike(Long boardId, HttpServletRequest request) {

        Map<String, Object> info = new HashMap<>();
        info.put("boardId", boardId);

        EventLog eventLog = eventLogFactory.createEventLog(
                "wish_board_like",
                SecurityUtils.getCurrentUserEmailOrGuest(),
                LogRequestUtils.getClientIp(request),
                LogRequestUtils.getDevice(request),
                LogRequestUtils.getPageUrl(request),
                info
        );

        eventLogger.logWishEvent(eventLog);
    }

    // 댓글 좋아요 했을 때
    public void logWishCommentLike(Long commentId, HttpServletRequest request) {

        Map<String, Object> info = new HashMap<>();
        info.put("commentId", commentId);

        EventLog eventLog = eventLogFactory.createEventLog(
                "wish_comment_like",
                SecurityUtils.getCurrentUserEmailOrGuest(),
                LogRequestUtils.getClientIp(request),
                LogRequestUtils.getDevice(request),
                LogRequestUtils.getPageUrl(request),
                info
        );

        eventLogger.logWishEvent(eventLog);
    }
}
