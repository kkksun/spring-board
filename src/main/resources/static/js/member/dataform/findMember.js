const findMember = (page) => {
    fetch("/api/member/find/"+memberId)
        .then(response=> {
            if (!response.ok) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
            return response.json();
        }).then(data => {
            Object.keys(data).forEach(key => {
                if (document.getElementById(key) != undefined) {
                    document.getElementById(key).value = data[key]
                }
                // 관리 페이지 회원 수정에서 체크박스에 체크 (admin은 type, status 모두 disabled)
                if(page === "edit" && requestedPage === "MANAGE" && (key === "type" || key === "status")) {
                    document.querySelectorAll(`.${key}`).forEach((box) => {
                        if(data["loginId"] === "admin") {
                            box.disabled = true;
                        }
                        if(box.value === data[key]) {
                            box.checked = true;
                        }
                    })
                }
            })

            if (page === "info"){
                loginIdOfMember = data["loginId"];
                statusOfMember = data["status"];
            }
            if(page === "edit") {
                password.value = null;
                memberPassword = data["password"];
            }
        }).catch(err => alert(err))
}

