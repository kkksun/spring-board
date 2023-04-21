package project.springboard.domain.board.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

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

    @CreatedDate
    @Column(name = "CREATE_DT", updatable = false)
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "MODIFY_DT")
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
