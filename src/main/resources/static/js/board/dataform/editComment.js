const editComment = (id) => {
    const editBox = document.getElementById("writer"+id);
    const editComment = editBox.getElementsByClassName("commentInBoxText")[0].value;
    const currentComment = document.getElementById("comment"+id);

    const param = {
        comment: editComment
    }

    axios.patch("/api/comment/edit/"+id, {
        comment: editComment
    }).then(response => {
        if(response.status != 200) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        if(response.data.length !=  0) {
            createCommentListHtml(true, response.data);
        }
    }).catch(err => alert(err));


    // fetch("/api/comment/edit/"+id, {
    //     method: "PATCH",
    //     headers: {
    //         "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(param)
    // }).then(response => {
    //     if(!response.ok) {
    //         throw new Error(response.status + " 오류가 발생하였습니다.")
    //     }
    //     return response.json();
    // }).then(data => {
    //     if(data.length !=  0) {
    //         createCommentListHtml(true, data);
    //     }
    // }).catch(err => alert(err));
}