const createMemberListHtml = (data) => {
    let table = document.getElementsByClassName( "memberList")[0];
    let listTr;
    data.forEach(m => {
        listTr = document.createElement("tr");
        Object.keys(m).forEach(key => {
            let td = document.createElement("td");
                td.setAttribute("id", key);
                td.setAttribute("class", key);
            if (key === "loginId") {
                const url = "/manage/info/" + m["id"];
                let child = document.createElement("a");
                child.setAttribute("href", url);
                child.innerText = m[key];
                td.appendChild(child);
                listTr.appendChild(td);
            } else if (key != "id") {
                td.innerText = m[key];
                listTr.appendChild(td);
            }
        })
        table.append(listTr);
        listTr = null;
    })
}