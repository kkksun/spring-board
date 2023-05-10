const login = () => {
    const form = document.getElementById("form");
    const param = {
        loginId : form.loginId.value,
        password : form.password.value
    }
    fetch("/login", {
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

                Object.keys(data).forEach(key => {
                    if(key === "global") {
                        let parent = document.createElement("div");
                        let child = document.createElement("p");
                        child.setAttribute("id", `${key}Error`);
                        child.setAttribute("class", "field-error");
                        child.innerText = data[key];
                        parent.appendChild(child);
                        document.getElementsByClassName("line")[0].before(parent);
                    } else {
                        let div =  document.createElement("div");
                        div.setAttribute("id", `${key}Error`);
                        div.setAttribute("class", "field-error");
                        div.innerText = data[key];
                        document.getElementById(key).after(div);
                    }
                })
            }
        }).catch(err => {
        alert("오류가 발생하였습니다.")
        console.log(err);
    })
}