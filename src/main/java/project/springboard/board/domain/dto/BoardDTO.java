package project.springboard.board.domain.dto;

import lombok.*;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.form.AddBoardForm;
import project.springboard.board.domain.entity.Board;
import project.springboard.member.domain.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

            }

            public BoardDTO(AddBoardForm form) {
                    this.title = form.getTitle();
                    this.content = form.getContent();
                    this.member = new MemberDTO();
                    this.member.setId(form.getUserId());
                    attachFileList = form.getAttachFileList().stream().filter(f -> !f.isEmpty()).map(AttachFileDTO::new).collect(Collectors.toList());
            }

            public static BoardDTO toBoardDTO(Board board) {
                    BoardDTO viewBoard = BoardDTO.builder()
                            .id(board.getId())
                            .member(MemberDTO.toMemberDTO(board.getMember()))
                            .title(board.getTitle())
                            .content(board.getContent())
                            .delCheck(board.getDelCheck())
                            .createDt(board.getCreateDt())
                            .modifyDt(board.getModifyDt())
                            .attachFileList(board.getAttachFileList().stream()
                                                                     .filter(f -> f.getDelCheck() == Check.N)
                                                                     .map(AttachFileDTO::new)
                                                                     .collect(Collectors.toList()))
                            .build();
                    return viewBoard;
            }
}


