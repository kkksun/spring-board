const memberList = () => {
    fetch("/api/member/list")
        .then(response => {
            if(!response.ok) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
            return response.json();
        }).then(data => {
        createMemberListHtml(data);
    }).catch(err => alert(err));
}