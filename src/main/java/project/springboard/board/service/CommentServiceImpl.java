package project.springboard.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
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
        return CommentDTO.toCommentDtoList(comments);
    }

    /**
     * 댓글 등록
     */

    @Override
    @Transactional
    public List<CommentDTO> addComment(CommentDTO addComment) {
        Member findMember = memberRepository.findById(addComment.getMember().getId()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Board findBoard = boardRepository.findBoardById(addComment.getBoard().getId()).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Long parentId = addComment.getParent() == null ? null : addComment.getParent().getId();
        Comment parent = parentId == null ? null : commentRepository.findById(parentId).get();
        CommentLevelDTO commentLevel = commentRepository.referGroupIdAndLevelId(addComment.getBoard().getId(), parentId);

        Comment comment = Comment.createComment(findMember, findBoard, addComment, commentLevel, parent);
        commentRepository.save(comment);

        return commentList(comment.getBoard().getId());
    }

    @Override
    @Transactional
    public List<CommentDTO> editComment(Long commentId, CommentDTO editComment) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.setComment(editComment.getComment());

        return commentList(comment.getBoard().getId());
    }

    @Override
    @Transactional
    public List<CommentDTO> deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        Long boardId = comment.getBoard().getId();
        deleteCommentAndDeletedComment(comment);

        return commentList(boardId);
    }

    public void deleteCommentAndDeletedComment(Comment comment) {
        if(comment.getChildCnt() != 0) {
            comment.setDelCheck(Check.Y);
        } else {
            if (comment.getParent() != null) {
                comment.getParent().setChildCnt(comment.getParent().getChildCnt() - 1);
                if (comment.getParent().getChildCnt() == 0 && comment.getParent().getDelCheck() == Check.Y) {
                    deleteCommentAndDeletedComment(comment.getParent());
                }
            }
            commentRepository.deleteById(comment.getId());
        }
    }
}
