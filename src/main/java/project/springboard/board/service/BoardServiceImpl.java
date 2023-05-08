package project.springboard.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriUtils;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.entity.AttachFile;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.repository.AttachFileRepository;
import project.springboard.board.repository.BoardRepository;
import project.springboard.member.domain.entity.Member;
import project.springboard.global.exception.CustomException;
import project.springboard.global.exception.ErrorCode;
import project.springboard.global.paging.PagingParam;
import project.springboard.board.repository.BoardJpaRepository;
import project.springboard.member.repository.MemberJpaRepository;
import project.springboard.member.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static project.springboard.global.paging.PagingParam.*;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final AttachFileRepository attachFileRepository;


    @Value("${file.dir}")
    private String fileDir;

    /**
     * 게시글 리스트 조회
     */
    @Transactional
    @Override
    public List<BoardDTO> allBoard() {
        return boardRepository.findAll().stream()
                              .filter(b -> b.getDelCheck() != Check.N)
                              .map(BoardDTO :: new)
                              .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                              .collect(Collectors.toList());
    }

    @Override
    public PagingParam pageBoardList(int page) {
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by(Sort.Direction.DESC, "createDt"));
        Page<Board> pageBoardList = boardRepository.findByDelCheck(Check.N, pageRequest);

        return new PagingParam(pageBoardList);

    }

    /**
     * 게시글 등록
     */
    @Override
    @Transactional
    public void addBoard(BoardDTO addBoard) throws IOException {

        Member member = memberRepository.findById(addBoard.getMember().getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        List<AttachFile> attachFiles = null;
        if(!addBoard.getAttachFileList().isEmpty()) {
            attachFiles = addBoard.getAttachFileList().stream().map(AttachFile::createAttachFile)
                            .collect(Collectors.toList());
        }
        Board board = Board.createBoard(addBoard, member, attachFiles);
       boardRepository.save(board);

       if(!board.getAttachFileList().isEmpty()) {
           for (AttachFile attachFile : board.getAttachFileList()) {
               attachFile.setPath(createAddFolderPath(board.getId()));
               saveFile(attachFile);
           }
       }
    }


    /**
     * 게시글 조회
     */
    @Override
    public BoardDTO findBoard(Long boardId) {
        return BoardDTO.toBoardDTO(boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
    }

    /**
     * 게시글 수정
     */
    @Override
    @Transactional
    public void editBoard(Long boardId, BoardDTO editBoard, List<Long> preFileIdList) throws IOException {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        board.setTitle(editBoard.getTitle());
        board.setContent(editBoard.getContent());

        if(!board.getAttachFileList().isEmpty() ) {
           if(preFileIdList != null) { //이전과 동일 또는 일부 삭제
               board.getAttachFileList().stream().filter(f -> !preFileIdList.contains(f.getId())).forEach(f -> f.setDelCheck(Check.Y));
               List<AttachFile> deleteFileList = board.getAttachFileList().stream().filter(f -> !preFileIdList.contains(f.getId())).collect(Collectors.toList());
               for (AttachFile attachFile : deleteFileList) {
                   deleteFile(createDeleteFilePath(attachFile));
               }
           } else { // 이전 파일 전체 삭제
               board.getAttachFileList().stream().forEach(f -> f.setDelCheck(Check.Y));
               deleteFolder(createDeleteFolderPath(boardId));
           }
        }

        if(!editBoard.getAttachFileList().isEmpty()) { // 신규로 업로드 파일이 있는 경우
            List<AttachFile> newAttachFiles =  editBoard.getAttachFileList().stream().map(AttachFile::createAttachFile)
                        .collect(Collectors.toList());

            for (AttachFile newAttachFile : newAttachFiles) {
                newAttachFile.setPath(createAddFolderPath(board.getId()));
                board.addAttachFile(newAttachFile);
                saveFile(newAttachFile);
            }
        }

    }

    /**
     * 게시글 삭제
     */
    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        board.setDelCheck(Check.Y);

        board.getAttachFileList().stream().forEach(file -> file.setDelCheck(Check.Y));
        deleteFolder(createDeleteFolderPath(boardId));

    }

    /**
     * 첨부 파일 저장
     */
    public void saveFile(AttachFile attachFile) throws IOException {
        String directory = fileDir + attachFile.getPath();
        String fullPath = directory + attachFile.getServerFileName();
        if(!new File(directory).exists()) {
            Files.createDirectories(Paths.get(directory));
        }
        attachFile.getMultipartFile().transferTo(new File(fullPath));
    }


    /**
     * 첨부 파일 다운로드
     */

    public UrlResource fileDownload(Long fileId, HttpHeaders headers) throws MalformedURLException {
        AttachFile attachFile = attachFileRepository.findById(fileId).orElseThrow(()-> new CustomException(ErrorCode.FILE_NOT_FOUND));
        String originalFileName = attachFile.getOriginalFileName();
        String serverFileName = attachFile.getServerFileName();

        String fullPath = fileDir + attachFile.getPath() + serverFileName;

        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);


        return new UrlResource("file:" + fullPath );
    }



    /**
     * 폴더 삭제
     */
    public void deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if(folder.exists()){
            File[] files = folder.listFiles();
            for (File file : files) {
                if(file.isFile()) {
                    file.delete();
                } else  {
                    deleteFolder(file.getPath());
                }
            }
            folder.delete();
        }
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String deleteFilePath) {
        File file = new File(deleteFilePath);
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     *파일 경로 생성
     */

    public String createDeleteFilePath(AttachFile file) { return fileDir + file.getPath() + file.getServerFileName(); }
    /**
     *폴더 경로 생성
     */
    public String createDeleteFolderPath(Long boardId) { return  fileDir + "/" + boardId; }
    public String createAddFolderPath(Long boardId) { return  "/" + boardId + "/file/"; }

}
