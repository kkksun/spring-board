const findCommentList = (boardId) => {
    fetch("/api/comment/list/" + boardId).then(response => {
        if(!response.ok) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        return response.json();
    }).then(data => {
        document.getElementById("commentList").innerHTML ="";
        if(data.length !=  0) {
            createCommentListHtml(true, data);
        }
        deleteBoard(data)
    }).catch(err => alert(err));
}