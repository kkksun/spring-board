package project.springboard.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class BoardDTO {

        private Long id;

        private Member member;

        private String title;

        private String content;

        private Check delCheck;

        private Check noticeCheck;

        private LocalDateTime createDt;

        private LocalDateTime modifyDt;

        private List<AttachFile> attachFileList = new ArrayList<>();

            public BoardDTO(Board board) {
                    this.id = board.getId();
                    this.member =  board.getMember();
                    this.title = board.getTitle();
                    this.content = board.getContent();
                    this.delCheck = board.getDelCheck();
                    this.noticeCheck = board.getNoticeCheck();
                    this.createDt = board.getCreateDt();
                    this.modifyDt = board.getModifyDt();
            }
}



