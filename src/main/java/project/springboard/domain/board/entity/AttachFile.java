package project.springboard.domain.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
public class AttachFile {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "file_name")
    private String fileName;

    private String path;

    @Enumerated(EnumType.STRING)
    private Check delCheck;



}
