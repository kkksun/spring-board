const createBoardListHtml = (data, existBoard) => {
    let table = document.getElementsByClassName( "boardList")[0];
    let listTr;
    if(!existBoard) {
        listTr = document.createElement("tr");
        let child = document.createElement("td");
        child.setAttribute("colspan", "4")
        child.innerText="등록된 게시글이 없습니다.";
        listTr.appendChild(child);
        table.append(listTr);
    } else {
        data.forEach(m => {
            const url = "/board/view/" + m["id"];
            listTr = document.createElement("tr");
            listTr.appendChild(createTdTagHtml("id", m["id"], null));
            listTr.appendChild(createTdTagHtml("title", m["title"], url));
            listTr.appendChild(createTdTagHtml("loginId", m["loginId"], null));
            listTr.appendChild(createTdTagHtml("createDate", m["createDate"], null));
            table.append(listTr);
            listTr = null;
        })
    }
}

function createTdTagHtml(key, value, url) {
    let td = document.createElement("td");
    td.setAttribute("id", key);
    td.setAttribute("class", key);
    if (key === "title") {
        let child = document.createElement("a");
        child.setAttribute("href", url);
        child.innerText = value;
        td.appendChild(child);
    } else {
        // td.innerText = key === "createDate" ? value.toString().substring(0, 10) +" " + value.toString().substring(11, 16) : value;
        td.innerText = value;
    }
    return td;
}