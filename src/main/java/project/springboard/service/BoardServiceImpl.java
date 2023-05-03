package project.springboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriUtils;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.member.entity.Member;
import project.springboard.exception.CustomException;
import project.springboard.exception.ErrorCode;
import project.springboard.paging.PagingParam;
import project.springboard.repository.BoardRepository;
import project.springboard.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static project.springboard.paging.PagingParam.*;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 게시글 리스트 조회
     */
    @Transactional
    @Override
    public List<BoardDTO> allBoard() {
//        List<Board> boards = boardRepository.boardList();

        return boardRepository.boardList().stream()
                                          .map(BoardDTO ::new)
                                          .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                                          .collect(Collectors.toList());
    }

    /**
     * 게시글 페이징
     */
    @Override
    public PagingParam boardPaging(int currentPage) {

        Long totalBoardCount = boardRepository.allBoardCount();


        int totalPageCount = totalBoardCount == 0 ? 1 : (int) Math.ceil((double)totalBoardCount/ pageSize);
        int offset = (pageSize * (currentPage-1)) +1 ;
        int startPage = ((currentPage -1) / blockSize) * blockSize + 1;
        int endPage = totalPageCount == 1? 1 : startPage + blockSize - 1;
        int prevPage = currentPage == 1? currentPage : currentPage - 1 ;
        int nextPage = currentPage == 1? currentPage : currentPage + 1;

        if(currentPage > totalPageCount) {
            currentPage = totalPageCount;
        }

        if(endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        if(nextPage > totalPageCount) {
            nextPage = totalPageCount;
        }

        return PagingParam.builder()
                .currentPage(currentPage)
                .offset(offset)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(totalPageCount)
                .prevPage(prevPage)
                .nextPage(nextPage)
                .build();
    }

    @Override
    public List<BoardDTO> pageBoardList(int offset, int limit) {
       return boardRepository.findBoardPaging(offset, limit).stream().map(BoardDTO ::new)
                .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                .collect(Collectors.toList());

    }

    /**
     * 게시글 등록
     */
    @Override
    @Transactional
    public void addBoard(BoardDTO addBoard) throws IOException {

        Member member = memberRepository.findByMember(addBoard.getMember().getId());

        List<AttachFile> attachFiles = null;
        if(!addBoard.getAttachFileList().isEmpty()) {
            attachFiles = addBoard.getAttachFileList().stream().map(AttachFile::createAttachFile)
                            .collect(Collectors.toList());
        }

        Board board = Board.createBoard(addBoard, member, attachFiles);

       boardRepository.addBoard(board);

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
        Board board = boardRepository.findBoard(boardId);

        if(board == null) {new CustomException(ErrorCode.BOARD_NOT_FOUND);};
        return BoardDTO.toBoardDTO(board);
    }

    /**
     * 게시글 수정
     */
    @Override
    @Transactional
    public void editBoard(Long boardId, BoardDTO editBoard, List<Long> preFileIdList) throws IOException {
        Board board = boardRepository.findBoard(boardId);
        board.setTitle(editBoard.getTitle());
        board.setContent(editBoard.getContent());

        if(!board.getAttachFileList().isEmpty() ) {
           if(preFileIdList != null) {
               board.getAttachFileList().stream().filter(f -> !preFileIdList.contains(f.getId())).forEach(f -> f.setDelCheck(Check.Y));
               List<AttachFile> deleteFileList = board.getAttachFileList().stream().filter(f -> !preFileIdList.contains(f.getId())).collect(Collectors.toList());
               for (AttachFile attachFile : deleteFileList) {
                   deleteFile(createDeleteFilePath(attachFile));
               }
           } else {
               board.getAttachFileList().stream().forEach(f -> f.setDelCheck(Check.Y));
               deleteFolder(createDeleteFolderPath(boardId));
           }
        }

        if(!editBoard.getAttachFileList().isEmpty()) {
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
        Board board = boardRepository.findBoard(boardId);
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
        AttachFile attachFile = boardRepository.fileDownload(fileId);
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
