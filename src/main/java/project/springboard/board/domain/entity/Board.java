package project.springboard.board.domain.entity;


import lombok.*;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.entity.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Auditable<Long>  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN")
    private Check delCheck;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<AttachFile> attachFileList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();


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
