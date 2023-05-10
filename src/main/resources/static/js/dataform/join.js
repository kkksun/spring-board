
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

        fetch("/join", {
            method: "post",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(param)
        }).then(response => {
            if (!response.ok) {
                throw new Error('오류가 발생하였습니다. 다시 시도해 주세요.');
            }
            return response.json();
        }).then(data => {
            if (JSON.stringify(data) === '{}') {
                location.href = window.location.origin
            } else {
                let errorTag = document.querySelectorAll(".field-error");
                if (errorTag.length != 0) {
                    errorTag.forEach(tag => tag.remove())
                }

                console.log(data);
                Object.keys(data).forEach(key => {
                    if (key === "global") {
                        const parent = document.createElement("div");
                        let child = document.createElement("p");
                        child.setAttribute("id", `${key}Error`);
                        child.setAttribute("class", "field-error");
                        child.innerText = data[key];
                        parent.appendChild(child);
                        document.getElementsByClassName("line")[0].before(parent);
                    } else {
                        let div = document.createElement("div");
                        div.setAttribute("id", `${key}Error`);
                        div.setAttribute("class", "field-error");
                        div.innerText = data[key];
                        if(key === "type") {
                            document.getElementsByClassName("radio_btn")[0].after(div)
                        } else {
                            document.getElementById(key).after(div);
                        }
                    }
                })
            }
        }).catch(err => {
            alert(err)
            console.log(err);
        })
    }
}