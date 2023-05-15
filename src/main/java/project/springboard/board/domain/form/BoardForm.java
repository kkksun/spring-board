package project.springboard.board.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import project.springboard.board.domain.dto.BoardDTO;

import java.time.LocalDateTime;

@Data
public class BoardForm {

    private Long id;
    private String title;
    private String LoginId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    public BoardForm(BoardDTO board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.LoginId = board.getMember().getLoginId();
        this.createDate = board.getCreateDate();
    }

}
