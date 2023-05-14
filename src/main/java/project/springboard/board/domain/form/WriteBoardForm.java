package project.springboard.board.domain.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class WriteBoardForm {

    private Long memberId;

    @NotBlank
    private String title;

    private String content;

    private List<MultipartFile> attachFileList = new ArrayList<>();

    private List<Long> preFileIdList;
}
