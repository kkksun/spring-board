package project.springboard.domain.board.form;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddBoardForm {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Check noticeCheck;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;

    private List<AttachFile> attachFileList = new ArrayList<>();
}
