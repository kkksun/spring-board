package project.springboard.domain.board.entity;

import lombok.Getter;
import lombok.Setter;
import project.springboard.domain.board.dto.AttachFileDTO;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter @Setter
public class AttachFile {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "original_file_name")
    private String originalFilename;

    @Column(name = "server_file_name")
    private String serverFileName;

    private String path;

    @Enumerated(EnumType.STRING)
    private Check delCheck;

    public AttachFile() {
    }


    public static AttachFile createAttachFile(AttachFileDTO attachFile) {
        AttachFile file = new AttachFile();
        file.setOriginalFilename(attachFile.getOriginalFilename());
        file.setServerFileName(attachFile.getServerFileName());
        file.setDelCheck(Check.N);
        return file;
    }
}
