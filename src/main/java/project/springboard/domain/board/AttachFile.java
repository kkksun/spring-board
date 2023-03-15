package project.springboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class AttachFile {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "file_name")
    private String fileName;

    private String path;

    @Enumerated(EnumType.STRING)
    private Check delCheck;


}
