const findBoard = (boardId, isEdit) => {
    if(boardId == null) {
        alert("오류가 발생하였습니다.");
    } else {
        fetch("/api/board/view/"+boardId).then(response => {
            if(!response.ok) {
                throw new Error();
                // console.log(response.body);
            }
            return response.json();
        }).then(data => {
            Object.keys(data).forEach(key => {
                if(document.getElementById(key) != undefined && key != "attachFileList") {
                    if(isEdit && key === "title") {document.getElementById(key).value = data[key];}
                    else if(key === "content") {
                        document.getElementById(key).style.whiteSpace= "pre-line";
                        document.getElementById(key).innerText = data[key];
                    }
                    else { document.getElementById(key).innerText = data[key] };
                }
                if(key === "attachFileList") {
                    let fileList = [];
                    fileList = data[key];
                    let parent;
                    let div;
                    if(!isEdit) {
                         parent = document.getElementById("fileList");
                        if(fileList.length == 0) {document.getElementById("fileList").innerText = "첨부 파일";}
                        fileList.forEach(file => {
                            div = document.createElement("div");
                            const a = document.createElement("a");
                            a.setAttribute("href", "/api/file/" + file.id);
                            a.innerText = file.originalFileName;
                            div.appendChild(a);
                            parent.append(div);
                        })
                    } else {
                        if(fileList.length != 0) {document.getElementById("default").innerText = "";}
                        parent = document.getElementById("preFileList") ;
                        fileList.forEach(file => {
                            div = document.createElement("div");
                            div.setAttribute("id", file.id);
                            div.innerText = file.originalFileName;
                            let btn = document.createElement("button");
                                btn.setAttribute("type", "button");
                                btn.setAttribute("data-fileid", file.id);
                                btn.setAttribute("class", "btn_preRemove");
                                btn.innerText ="×";
                            let input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("name", "preFileIdList");
                                input.value=file.id;
                            div.appendChild(btn);
                            div.appendChild(input);
                            parent.append(div);
                        })
                        Array.from(document.getElementsByName("preFileIdList")).forEach(file=> preFileList.push(file.value));
                    }
                }
                if(key === "memberId") {
                    memberIdOfBoard = data[key];
                }
            })
        }).catch(err => alert(err))
    }
}