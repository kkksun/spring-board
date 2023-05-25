const boardList = (page) => {
    axios.get('/api/board/list?page=' + page).then(response => {
        if(response.status != 200) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        const data = response.data;
        console.log(data);
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