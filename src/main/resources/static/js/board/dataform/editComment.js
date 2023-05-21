const editComment = (id) => {
    const editBox = document.getElementById("writer"+id);
    const editComment = editBox.getElementsByClassName("commentInBoxText")[0].value;
    const currentComment = document.getElementById("comment"+id);

    const param = {
        comment: editComment
    }

    fetch("/api/comment/edit/"+id, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(param)
    }).then(response => {
        if(!response.ok) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        return response.json();
    }).then(data => {
        if(data.length !=  0) {
            createCommentListHtml(true, data);
        }
    }).catch(err => alert(err));
}