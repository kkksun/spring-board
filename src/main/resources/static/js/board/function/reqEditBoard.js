const reqEditBoard = () => {
    if(memberIdOfBoard === idOfLoginMember || typeOfLoginMember != "USER") {
        location.href = window.location.origin + "/board/edit/" + boardId;
    }
}