const deleteMember = () => {
    let reqUrl ="/api/delete/member/" + memberId + "?isMember=" + (requestedPage === "MEMBER"?  true : false);
    let url = window.location.origin;

    if(requestedPage === "MANAGE" && typeOfLoginMember === "MANAGER" ) {
        alert("Manager는 회원 조회만 가능합니다.")
    } else if (loginIdOfMember === "admin") {
        alert("admin 계정은 탈퇴 불가합니다.");
    } else {
        if(confirm('탈퇴하시겠습니까?')) {
            fetch(reqUrl, {
                method : "DELETE"
            }).then(response => {
                if (response.ok) {
                    alert("탈퇴가 완료되었습니다.")
                    location.href = url + (requestedPage === "MEMBER"?  "" : "/manage/members")
                } else {
                    throw new Error('회원 정보가 존재하지 않습니다.');
                }
            }).catch(err => alert(err))
        }
    }
}