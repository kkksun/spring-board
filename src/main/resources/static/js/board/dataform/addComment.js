const addComment = (isFirst, parentId) => {

    axios.post("/api/comment/add", {
            memberId: idOfLoginMember,
            boardId: boardId,
            comment: document.getElementsByClassName("commentInBoxText")[0].value,
            parentId: parentId
    })
    .then(response => {
        console.log(response);
        if(response.status != 200) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        if(response.data.length !=  0) {
            createCommentListHtml(true, response.data);
        }
    }).catch(err => {
        alert(err)
    })
}