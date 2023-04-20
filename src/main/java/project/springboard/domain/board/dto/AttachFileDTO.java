package project.springboard.domain.board.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;

import java.util.UUID;


@Data
public class AttachFileDTO {
    private Long id;

    private Board board;

    private String originalFilename;

    private String serverFileName;

    private String path;

    private Check delCheck;

    private MultipartFile multipartFile;

    public AttachFileDTO(MultipartFile multipartFile) {
        this.originalFilename = multipartFile.getOriginalFilename();
        this.serverFileName = changeServerFileName(multipartFile.getOriginalFilename());
        this.multipartFile = multipartFile;
    }

    public AttachFileDTO(AttachFile attachFile) {
        this.id = attachFile.getId();
        this.board = attachFile.getBoard();
        this.originalFilename = attachFile.getOriginalFilename();
        this.serverFileName = getServerFileName();
        this.path = getPath();
    }


    private String changeServerFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        int pos = originalFilename.lastIndexOf(".");
        return uuid + "." + originalFilename.substring(pos+1);
    }


}
