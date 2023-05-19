package project.springboard.board.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.board.domain.dto.AttachFileDTO;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.entity.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class AttachFile extends Auditable<Long> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "SERVER_FILE_NAME")
    private String serverFileName;

    @Column(name ="FILE_PATH")
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name ="DEL_YN")
    @ColumnDefault("'N'")
    private Check delCheck;

    @Transient
    private MultipartFile multipartFile;


    public static AttachFile createAttachFile(AttachFileDTO attachFile) {
        AttachFile file = new AttachFile();
        file.setOriginalFileName(attachFile.getOriginalFileName());
        file.setServerFileName(attachFile.getServerFileName());
        file.setMultipartFile(attachFile.getMultipartFile());
        return file;
    }


}
