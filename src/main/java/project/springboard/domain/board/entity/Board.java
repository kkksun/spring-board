package project.springboard.domain.board.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn")
    private Check delCheck;

/*    @Enumerated(EnumType.STRING)
    @Column(name = "notice_yn")
    private Check noticeCheck;*/

    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "modify_dt")
    private LocalDateTime modifyDt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<AttachFile> attachFileList = new ArrayList<>();

    public void addAttachFile(AttachFile file) {
        attachFileList.add(file);
        file.setBoard(this);
    }

    public static Board createBoard(BoardDTO boardDTO, Member member, List<AttachFile> attachFiles ){
        Board board = new Board();
        board.setMember(member);
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setDelCheck(Check.N);
        if(attachFiles != null) {
            for (AttachFile file : attachFiles) {
                board.addAttachFile(file);
            }
        }
        return board;

    }

}
