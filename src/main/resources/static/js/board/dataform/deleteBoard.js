const deleteBoard = () => {
    if(memberIdOfBoard === idOfLoginMember || typeOfLoginMember != "USER") {
        if(confirm("삭제하시겠습니까?")) {
            fetch("/api/board/delete/" + boardId, {
                method : "DELETE"
            }).then(response => {
                if (response.ok) {
                    alert("삭제가 완료되었습니다.")
                    location.href = document.location.origin + "/board?page=1"
                } else {
                    throw new Error('오류가 발생하였습니다.');
                }
            }).catch(err => alert(err))
        }
    }
}