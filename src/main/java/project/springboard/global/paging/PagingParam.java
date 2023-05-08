package project.springboard.global.paging;


import lombok.*;
import org.springframework.data.domain.Page;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.member.domain.dto.MemberDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingParam {

    public static final int pageSize = 3;
    public static final int blockSize = 5;

    private int currentPage;
    private int offset ;
    private int startPage;
    private int endPage;
    private int totalPage;
    private int prevPage;
    private int nextPage;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;


    private List<BoardDTO> boardList = new ArrayList<>();
    private List<MemberDTO> MemberList = new ArrayList<>();

    public PagingParam(Page<Board> pageBoardList) {
        this.boardList = pageBoardList.getContent().stream()
                                       .map(BoardDTO :: new)
                                       .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                                       .collect(Collectors.toList());
        this.totalPage = pageBoardList.getTotalPages();
        this.currentPage = pageBoardList.getNumber()+1;
        this.hasNext = pageBoardList.hasNext();
        this.hasPrevious = pageBoardList.hasPrevious();
        this.isFirst = pageBoardList.isFirst();
        this.isLast = pageBoardList.isLast();
        pageCalculation();
    }

    private void pageCalculation () {
        this.startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        this.endPage = totalPage == 1? 1 : startPage + blockSize - 1;
        this.prevPage = isFirst ? currentPage : currentPage - 1 ;
        this.nextPage = hasNext ? currentPage + 1 : currentPage;

        if(currentPage > totalPage) {
            currentPage = totalPage;
        }

        if(endPage > totalPage) {
            endPage = totalPage;
        }

        if(nextPage > totalPage) {
            nextPage = totalPage;
        }
    }


}
