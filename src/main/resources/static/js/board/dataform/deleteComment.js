const deleteComment = (id) => {
    if(confirm("댓글을 삭제하시겠습니까?")) {
        axios.delete("/api/comment/delete/" + id)
            .then(response => {
                if(response.status != 200) {
                    throw new Error(response.status + " 오류가 발생하였습니다.")
                }
                if(response.data.length !=  0) {
                    createCommentListHtml(true, response.data);
                } else {
                    document.getElementById("commentList").innerHTML = "";
                }
            }).catch(err => alert(err));

        // fetch("/api/comment/delete/" + id, {
        //     method: "DELETE"
        // }).then(response => {
        //     if(!response.ok) {
        //         throw new Error(response.status + " 오류가 발생하였습니다.")
        //     }
        //     return response.json();
        // }).then(data => {
        //     if(data.length !=  0) {
        //         createCommentListHtml(true, data);
        //     } else {
        //         document.getElementById("commentList").innerHTML = "";
        //     }
        // }).catch(err => alert(err));
    }
}