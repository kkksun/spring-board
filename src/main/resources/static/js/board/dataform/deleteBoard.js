const deleteBoard = (data) => {
    const commentCnt = data.length;
    if(memberIdOfBoard === idOfLoginMember && commentCnt != 0 && typeOfLoginMember == "USER") {
        alert("게시글 삭제가 불가합니다. 관리자에게 문의해 주세요.");
    }
    if ((memberIdOfBoard === idOfLoginMember && commentCnt == 0) || typeOfLoginMember != "USER") {
        if (confirm("삭제하시겠습니까?")) {
            axios.delete("/api/board/delete/" + boardId)
            .then(response => {
                if (response.status == 200) {
                    location.href = document.location.origin + "/complete/board?type=DELETE";
                } else {
                    throw new Error(response.status + " 오류가 발생하였습니다.")
                }
            }).catch(err => alert(err))
        }
    }
}