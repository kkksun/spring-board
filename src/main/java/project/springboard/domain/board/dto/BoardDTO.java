package project.springboard.domain.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.board.form.AddBoardForm;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardDTO {

        private Long id;

        private MemberDTO member;

        private String title;

        private String content;

        private Check delCheck;

        private LocalDateTime createDt;

        private LocalDateTime modifyDt;

        private List<AttachFileDTO> attachFileList ;

            public BoardDTO(Board board) {
                    this.id = board.getId();
                    this.member =  MemberDTO.toMemberDTO(board.getMember());
                    this.title = board.getTitle();
                    this.content = board.getContent();
                    this.delCheck = board.getDelCheck();
                    this.createDt = board.getCreateDt();
//                    this.noticeCheck = board.getNoticeCheck();
            }

            public BoardDTO(AddBoardForm form) {
                    this.title = form.getTitle();
                    this.content = form.getContent();
                    this.member = new MemberDTO();
                    this.member.setId(form.getUserId());
                    attachFileList = form.getAttachFileList().stream().filter(f -> !f.isEmpty()).map(AttachFileDTO::new).collect(Collectors.toList());
            }
}



