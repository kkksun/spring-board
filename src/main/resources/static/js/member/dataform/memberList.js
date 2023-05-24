const memberList = () => {
    axios.get("/api/member/list")
        .then(response => {
            if(response.status != 200) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
       createMemberListHtml(response.data);
    }).catch(err => alert(err));
}