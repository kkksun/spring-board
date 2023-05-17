package project.springboard.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.repository.BoardRepository;
import project.springboard.board.repository.CommentRepository;
import project.springboard.global.exception.CustomException;
import project.springboard.global.exception.ErrorCode;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 리스트 조회
     */
    @Override
    public List<CommentDTO> commentList(Long boardId) {
        List<Comment> comments = commentRepository.commentListByBoardId(boardId);
        List<CommentDTO> commentList = CommentDTO.toCommentDtoList(comments);
        return commentList;
    }

    /**
     * 댓글 등록
     */

    @Override
    @Transactional
    public CommentDTO addComment(CommentDTO addComment) {
        Member findMember = memberRepository.findById(addComment.getMember().getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Board findBoard = boardRepository.findById(addComment.getBoard().getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Long parentId = addComment.getParent() == null ? null : addComment.getParent().getId();
        Comment parent = parentId == null ? null : commentRepository.findById(parentId).get();
        CommentLevelDTO commentLevel = commentRepository.referGroupIdAndLevelId(addComment.getBoard().getId(), parentId);

        Comment comment = Comment.createComment(findMember, findBoard, addComment, commentLevel, parent);

        commentRepository.save(comment);

        return CommentDTO.toCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentDTO editComment(Long commentId, CommentDTO editComment) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        comment.setComment(editComment.getComment());

        return CommentDTO.toCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        log.info("commentId = {}", commentId);
        commentRepository.deleteById(commentId);
    }
}
