const deleteComment = (id) => {
    if(confirm("댓글을 삭제하시겠습니까?")) {
        fetch("/api/comment/delete/" + id, {
            method: "DELETE"
        }).then(response => {
            if(!response.ok) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
            return response.json();
        }).then(data => {
            if(data.length !=  0) {
                createCommentListHtml(true, data);
            } else {
                document.getElementById("commentList").innerHTML = "";
            }
        }).catch(err => alert(err));
    }
}