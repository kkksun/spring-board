package project.springboard.domain.board.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.domain.board.dto.AttachFileDTO;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AttachFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFilename;

    @Column(name = "SERVER_FILE_NAME")
    private String serverFileName;

    private String path;

    @Enumerated(EnumType.STRING)
    private Check delCheck;

    @CreatedDate
    @Column(name = "CREATE_DT", updatable = false)
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "MODIFY_DT")
    private LocalDateTime modifyDt;

    @Transient
    private MultipartFile multipartFile;


    public static AttachFile createAttachFile(AttachFileDTO attachFile) {
        AttachFile file = new AttachFile();
        file.setOriginalFilename(attachFile.getOriginalFilename());
        file.setServerFileName(attachFile.getServerFileName());
        file.setDelCheck(Check.N);
        file.setMultipartFile(attachFile.getMultipartFile());
        return file;
    }
}
