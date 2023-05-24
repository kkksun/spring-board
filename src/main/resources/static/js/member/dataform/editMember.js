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
    axios.patch("/api/member/edit/" + memberId, {
        password: isPwChange ? form.password.value : memberPassword,
        pwChange: isPwChange,
        userName: form.userName.value,
        email: form.email.value,
        type: selectedType,
        status: selectedStatus,
        requestedPage: requestedPage
    }).then(response => {
        if(response.status != 200) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        console.log(response.data);
        if (JSON.stringify(response.data) === '{}') {
            location.href = window.location.origin + (requestedPage === 'MANAGE'? "/manage/members" : `/member/info/${memberId}` );
        } else {
            let errorTag = document.querySelectorAll(".field-error");
            if (errorTag.length != 0) {
                errorTag.forEach(tag => tag.remove())
            }
            createErrorMsgHtml(response.data, true);
        }

    }).catch(err => alert(err))
}