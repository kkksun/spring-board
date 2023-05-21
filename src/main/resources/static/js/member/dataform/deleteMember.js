const deleteMember = () => {
    let reqUrl ="/api/member/delete/" + memberId + "?isMember=" + (requestedPage === "MEMBER"?  true : false);
    let url = window.location.origin + (requestedPage === "MEMBER"?  "/complete/delete/member" : "/manage/members");

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
                    if(requestedPage !== "MEMBER") {
                        alert("탈퇴가 완료되었습니다.")
                    }
                    location.href = url;
                } else {
                    throw new Error(response.status + " 오류가 발생하였습니다.")
                }
            }).catch(err => alert(err))
        }
    }
}