const boardList = (page) => {
    fetch('/api/board/list?page=' + page).then(response => {
        if(!response.ok) {
            throw new Error("오류가 발생하였습니다.")
        }
        return response.json();
    }).then(data => {
        const pageParam = {
            currentPage : data["currentPage"],
            startPage: data["startPage"],
            endPage: data["endPage"],
            totalPage: data["totalPage"],
            prevPage: data["prevPage"],
            nextPage: data["nextPage"]
        }
            createBoardListHtml(data["pagingBoardList"], data["totalCount"] != 0? true : false);
        createPagingHtml(pageParam)
    }).catch(err => alert(err));
}