package project.springboard.domain.board.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter @Setter
public class BoardListForm {

    private Long id;

    private String loginId;

    private String title;

    private String content;

    private LocalDateTime createDt;


    public BoardListForm (BoardDTO board) {
        this.id = board.getId();
        this.loginId = board.getMember().getLoginId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDt = board.getCreateDt();
    }
}
