package project.springboard.global.paging;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingParam {

    public static final int pageSize = 10;
    public static final int blockSize = 5;

    private int currentPage;
    private int offset ;
    private int startPage;
    private int endPage;
    private int totalPage;
    private int prevPage;
    private int nextPage;


}
