package project.springboard.domain.board;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
public class Post {
    private String title;
    private String content;
}
