package project.springboard.board.domain.form;

import lombok.Builder;
import lombok.Data;
import project.springboard.global.paging.PagingParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardListForm {

    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPage;
    private long totalCount;
    private int prevPage;
    private int nextPage;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    @Builder.Default
    private List<BoardForm> pagingBoardList = new ArrayList<>();


    public BoardListForm(PagingParam pagingParam) {
        this.currentPage = pagingParam.getCurrentPage();
        this.startPage = pagingParam.getStartPage();
        this.endPage = pagingParam.getEndPage();
        this.totalPage = pagingParam.getTotalPage();
        this.totalCount = pagingParam.getTotalCount();
        this.prevPage = pagingParam.getPrevPage();
        this.nextPage = pagingParam.getNextPage();
        this.hasNext = pagingParam.isHasNext();
        this.hasPrevious = pagingParam.isHasPrevious();
        this.isFirst = pagingParam.isFirst();
        this.isLast = pagingParam.isLast();

        this.pagingBoardList = pagingParam.getBoardList().stream()
                               .map(BoardForm :: new)
                               .sorted(Comparator.comparing(BoardForm::getCreatedDate).reversed())
                               .collect(Collectors.toList());


    }

}
