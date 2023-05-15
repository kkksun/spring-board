package project.springboard.board.domain.form;

import lombok.Data;
import project.springboard.board.domain.dto.BoardDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ViewBoardForm {
    private Long id;
    private Long memberId;
    private String LoginId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private List<AttachFileForm> attachFileList;

    public ViewBoardForm(BoardDTO findBoard) {
        this.id = findBoard.getId();
        this.memberId = findBoard.getMember().getId();
        this.LoginId = findBoard.getMember().getLoginId();
        this.title = findBoard.getTitle();
        this.content = findBoard.getContent();
        this.createDate = findBoard.getCreateDate();
        this.attachFileList = findBoard.getAttachFileList().stream().map(AttachFileForm :: new).collect(Collectors.toList());
    }
}
