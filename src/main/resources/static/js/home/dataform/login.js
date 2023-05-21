/*const login = () => {
    const form = document.getElementById("form");
    const param = {
        loginId : form.loginId.value,
        password : form.password.value
    }

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    console.log(header);
    console.log(token);
    const loginInfo = new FormData();
    loginInfo.append("loginId", form.loginId.value);
    loginInfo.append("password", form.password.value);

    fetch("/api/login", {
        method: "post",
        headers: {
            'header': header,
            'X-Requested-With': 'XMLHttpRequest',
            // "Content-Type": "application/x-www-form-urlencoded",
            // "Content-Type": "application/json",
            'X-CSRF-Token': token,
        },
        body: loginInfo,
    }).then(response => {
        if(!response.ok) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        return response.json()})
        .then(data => {
            if(JSON.stringify(data) === '{}') {
                location.href = window.location.origin + "/board?page=1"
            } else {
                let errorTag = document.querySelectorAll(".field-error");
                if(errorTag.length != 0) { errorTag.forEach(tag => tag.remove())}

                createErrorMsgHtml(data, false);
            }
        }).catch(err => {
        alert(err);
    })
}*/


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
            // 'X-CSRF-Token': token,
        },
        body: JSON.stringify(param),
    }).then(response => {
        if(!response.ok) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        return response.json()})
        .then(data => {
            if(JSON.stringify(data) === '{}') {
                location.href = window.location.origin + "/board?page=1"
            } else {
                let errorTag = document.querySelectorAll(".field-error");
                if(errorTag.length != 0) { errorTag.forEach(tag => tag.remove())}

                createErrorMsgHtml(data, false);
            }
        }).catch(err => {
        alert(err);
    })
}

