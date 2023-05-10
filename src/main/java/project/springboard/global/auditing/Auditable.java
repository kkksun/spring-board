package project.springboard.global.auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;
import project.springboard.member.domain.entity.Member;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

//    @CreatedBy
//    private Member createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false )
    private LocalDateTime createDate;

//    @LastModifiedBy
//    private Member modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifyDate;


}
