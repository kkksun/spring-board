
const joinMember = () => {
    if(!loginIdOk || !passwordOk) {
        alert("아이디 또는 비밀번호를 다시 확인해 주세요.")
    } else {
        const form = document.getElementById("form");
        let currentCheckedVal;
        form.querySelectorAll(".selectType").forEach((box) => {
            if (box.checked) {
                currentCheckedVal = box.value;
            }
        })

        const param = {
            loginId: form.loginId.value,
            password: form.password.value,
            passwordConfirm: form.passwordConfirm.value,
            userName: form.userName.value,
            email: form.email.value,
            type: currentCheckedVal
        }

        fetch("/api/join", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(param)
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.status + " 오류가 발생하였습니다.")
            }
            return response.json();
        }).then(data => {
               if (JSON.stringify(data) === '{}') {
                location.href = window.location.origin + "/complete/join";
            } else {
                let errorTag = document.querySelectorAll(".field-error");
                if (errorTag.length != 0) {
                    errorTag.forEach(tag => tag.remove())
                }
                createErrorMsgHtml(data, false);
            }
        }).catch(err => {
            alert(err)
        })
    }
}