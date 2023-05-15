const reqEditMember = (pwChange) => {
    let url = window.location.origin  + (requestedPage === "MEMBER"? "/member/" : "/manage/") + "edit/" + memberId + "?pwChange=" + pwChange;

    if(requestedPage === "MANAGE" && typeOfLoginMember === "MANAGER" ) {
        alert("Manager는 회원 조회만 가능합니다.")
    } else if (statusOfMember === "DELETE") {
        alert("탈퇴한 계정은 수정 불가합니다.");
    } else {
        location.href = url;
    }
}