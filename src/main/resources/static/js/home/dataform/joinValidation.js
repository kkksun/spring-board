let loginIdOk = false;
let passwordOk = false;

const loginIdDuplicateCheck = () => {
    let loginId = document.getElementById("loginId").value;
    let checkResult = document.getElementById("idDuplicateCheck");

    const param = {
        "loginId": loginId
    }

    if(loginId.trim().length == 0 || loginId == null) {
        checkResult.innerText ="아이디를 입력해주세요.";
    } else {
        fetch("/api/member/loginIdDuplicateCheck", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(param)
        })
            .then((response) => response.json())
            .then((data) => {
                if (data) {
                    checkResult.innerText = "이미존재하는 아이디입니다.";
                    loginIdOk = false;
                } else {
                    checkResult.innerText = "사용가능한 아이디입니다.";
                    loginIdOk = true;
                }
            })
            .catch(err => {console.log("Fetch Error", err);})
    }
}

const confirmPassword = () => {
    const password = document.querySelector('#password').value;
    const passwordConfirm = document.querySelector('#passwordConfirm').value;
    const submitBtn = document.querySelector('#submit');
    let message = document.querySelector('#passwordCheck');

    if(password === passwordConfirm) {
        message.innerText = "비밀번호가 일치합니다."
        passwordOk = true;
    } else {
        message.innerText = "비밀번호가 일치하지 않습니다.";
        passwordOk = false;
    }
}