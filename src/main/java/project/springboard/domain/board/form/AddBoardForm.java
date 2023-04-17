package project.springboard.domain.board.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.domain.board.entity.AttachFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddBoardForm {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;

    private List<MultipartFile> attachFileList;

    private Boolean fileCheck;
}
