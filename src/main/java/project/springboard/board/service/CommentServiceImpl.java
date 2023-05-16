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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */

    @Override
    public CommentDTO addComment(CommentDTO addComment) {
        Member findMember = memberRepository.findById(addComment.getMember().getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Board findBoard = boardRepository.findById(addComment.getBoard().getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Long parentId = addComment.getParent() == null ? null : addComment.getParent().getId();
        Comment parent = parentId == null ? null : commentRepository.findById(parentId).get();
        CommentLevelDTO commentLevel = commentRepository.referGroupIdAndLevelId(addComment.getBoard().getId(), parentId);

        Comment comment = Comment.createComment(findMember, findBoard, addComment, commentLevel, parent);

        commentRepository.save(comment);

        CommentDTO savedComment = CommentDTO.toCommentDto(comment);

        return savedComment;
    }
}
