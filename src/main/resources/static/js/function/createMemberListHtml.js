const createMemberListHtml = (data, req) => {
    let table = document.getElementsByClassName(req + "List")[0];
    let listTr;
    data.forEach(m => {
        listTr = document.createElement("tr");
        Object.keys(m).forEach(key => {
            if (key === "loginId") {
                const url = "/manage/info/" + m["id"];
                let parent = document.createElement("td");
                parent.setAttribute("id", key);
                parent.setAttribute("class", key);
                let child = document.createElement("a");
                child.setAttribute("href", url);
                child.innerText = m[key];
                parent.appendChild(child);
                listTr.appendChild(parent);
            } else if (key != "id") {
                let td = document.createElement("td");
                td.setAttribute("id", key);
                td.setAttribute("class", key);
                td.innerText = key === "createDate" ? m[key].toString().substring(0, 10) : m[key];
                listTr.appendChild(td);
            }
        })
        table.append(listTr);
        listTr = null;
    })
}