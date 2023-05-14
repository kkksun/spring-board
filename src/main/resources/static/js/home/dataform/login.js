const login = () => {
    const form = document.getElementById("form");
    const param = {
        loginId : form.loginId.value,
        password : form.password.value
    }
    fetch("/api/login", {
        method: "post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(param)
    }).then(response => response.json())
        .then(data => {
            if(JSON.stringify(data) === '{}') {
                location.href = window.location.origin + "/board?page=1"
            } else {
                let errorTag = document.querySelectorAll(".field-error");
                if(errorTag.length != 0) { errorTag.forEach(tag => tag.remove())}

                createErrorMsgHtml(data, false);
            }
        }).catch(err => {
        alert("오류가 발생하였습니다.")
        console.log(err);
    })
}