const createErrorMsgHtml = (data, isDelete) => {
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
            if (key === "type") {
                document.getElementsByClassName("radio_btn")[0].after(div);
            } else if (key === "password" && isDelete) {
                document.getElementById("pwChangeBtn").after(div);
            }else {
                document.getElementById(key).after(div);
            }
        }
    })
}