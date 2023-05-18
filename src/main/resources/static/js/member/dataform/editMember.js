const editMember = () => {
    const form = document.getElementById("form");
    let selectedType ;
    let selectedStatus;
    document.querySelectorAll(".type").forEach((box) => {if(box.checked) { selectedType = box.value}});
    document.querySelectorAll(".status").forEach((box) => {if(box.checked) { selectedStatus = box.value}});
    const param = {
        password: isPwChange ? form.password.value : memberPassword,
        pwChange: isPwChange,
        userName: form.userName.value,
        email: form.email.value,
        type: selectedType,
        status: selectedStatus,
        requestedPage: requestedPage
    }
    fetch("/api/member/edit/" + memberId, {
        method: "PATCH",
        headers: {"Content-Type": "application/json",
        },
        body: JSON.stringify(param)
    }).then(response => {
        if(!response.ok) {
            throw new Error("회원 정보 수정에 실패하였습니다.")
        }
        return response.json()
    }).then(data => {
        console.log(data);
        if (JSON.stringify(data) === '{}') {
            location.href = window.location.origin + (requestedPage === 'MANAGE'? "/manage/members" : `/member/info/${memberId}` );
        } else {
            let errorTag = document.querySelectorAll(".field-error");
            if (errorTag.length != 0) {
                errorTag.forEach(tag => tag.remove())
            }
            createErrorMsgHtml(data, true);
        }

    }).catch(err => alert(err))
}