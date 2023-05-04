package project.springboard.board.domain.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddBoardForm {

    private Long userId;

    @NotBlank
    private String title;

    private String content;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;

    private List<MultipartFile> attachFileList;

}
