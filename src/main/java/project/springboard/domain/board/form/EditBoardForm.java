package project.springboard.domain.board.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.springboard.domain.board.dto.AttachFileDTO;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditBoardForm {
    private Long id;

    private Long userId;

    @NotBlank
    private String title;

    private String content;

    private List<MultipartFile> newAttachFileList;

    private List<AttachFileDTO> preFileList;

    private List<Long> preFileIdList;

}
