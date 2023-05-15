package project.springboard.board.domain.form;

import lombok.Data;
import project.springboard.board.domain.dto.AttachFileDTO;

@Data
public class AttachFileForm {
    private Long id;
    private String originalFileName;

    public AttachFileForm(AttachFileDTO attachFile) {
        this.id = attachFile.getId();
        this.originalFileName = attachFile.getOriginalFileName();
    }
}
