const addComment = (isFirst, parentId) => {
    const param = {
        memberId: idOfLoginMember,
        boardId: boardId,
        comment: document.getElementsByClassName("commentInBoxText")[0].value,
        parentId: parentId
    }
    fetch("/api/comment/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(param)
    }).then(response => {
        if(!response.ok) {
            throw new Error("오류가 발생하였습니다.")
        }
        return response.json();
    }).then(data => {
        if(data.length !=  0) {
            createCommentListHtml(true, data);
        }
    }).catch(err => alert(err));
}