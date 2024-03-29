package project.springboard.board.domain.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
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
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Auditable<Long>  {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ID", name = "USER_ID")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN")
    @ColumnDefault("'N'")
    private Check delCheck;

    @Builder.Default
    @OneToMany(mappedBy = "board",orphanRemoval = true, cascade = CascadeType.PERSIST)
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
        if(attachFiles != null) {
            for (AttachFile file : attachFiles) {
                board.addAttachFile(file);
            }
        }
        return board;

    }

}
