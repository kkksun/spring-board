const findCommentList = (boardId) => {

    axios.get("/api/comment/list/" + boardId)
        .then(response => {
            if(response.status != 200) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
            document.getElementById("commentList").innerHTML ="";
            if(response.data.length !=  0) {
                createCommentListHtml(true, response.data);
            }
            deleteBoard(response.data)
        }).catch(err => alert(err));



}