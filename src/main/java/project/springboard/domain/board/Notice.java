package project.springboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notice {

    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Embedded
    private Post post;

    @Enumerated(EnumType.STRING)
    private Check showCheck;

    @Column(name = "show_start_dt")
    private LocalDateTime showStartDt;

    @Column(name = "show_end_dt")
    private LocalDateTime showEndDt;
}
