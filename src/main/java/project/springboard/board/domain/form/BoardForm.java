package project.springboard.board.domain.form;

import lombok.Data;
import project.springboard.board.domain.dto.BoardDTO;

import java.time.LocalDateTime;

@Data
public class BoardForm {

    private Long id;
    private String title;
    private String LoginId;
    private LocalDateTime createDate;

    public BoardForm(BoardDTO board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.LoginId = board.getMember().getLoginId();
        this.createDate = board.getCreateDate();
    }

}
