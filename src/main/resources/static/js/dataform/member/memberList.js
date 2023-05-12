const memberList = () => {
    fetch("/api/list/members")
        .then(response => {
            if(!response.ok) {
                throw new Error("오류가 발생하였습니다.")
            }
            return response.json();
        }).then(data => {
        createMemberListHtml(data, "member");
    }).catch(err => alert(err));
}