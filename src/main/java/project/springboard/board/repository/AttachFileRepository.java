package project.springboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboard.board.domain.entity.AttachFile;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long>, AttachFileCustomRepository {

}
