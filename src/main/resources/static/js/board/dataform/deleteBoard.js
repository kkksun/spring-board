const deleteBoard = () => {
    if(memberIdOfBoard === idOfLoginMember || typeOfLoginMember != "USER") {
        if(confirm("삭제하시겠습니까?")) {
            fetch("/api/board/delete/" + boardId, {
                method : "DELETE"
            }).then(response => {
                if (response.ok) {
                    location.href = document.location.origin + "/complete/board?type=DELETE";
                } else {
                    throw new Error('오류가 발생하였습니다.');
                }
            }).catch(err => alert(err))
        }
    }
}