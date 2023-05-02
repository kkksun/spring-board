package project.springboard.paging;


import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingParam {

    public static final int pageLimit = 5;
    public static final int limitPerPage = 5;

    private int currentPage;
    private int offset ;
    private int startPage;
    private int endPage;


}
