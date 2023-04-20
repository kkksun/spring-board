package project.springboard.domain.board.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.domain.board.entity.AttachFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
