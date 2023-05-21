const addBoard = () => {
    const form = document.getElementById("form");
    const param = new FormData();
    param.append("memberId", memberId);
    param.append("title", form.title.value);
    param.append("content", form.content.value);
    for(let file of form.attachFileList.files){
        param.append("attachFileList", file);
    }

    fetch("/api/board/add", {
        method: "POST",
        headers:{
        },
        body: param
    }).then(response => {
        if(!response.ok) {
            throw new Error(response.status + " 오류가 발생하였습니다.")
        }
        return response.json();
    }).then(data => {
        if (JSON.stringify(data) === '{}') {
            location.href = window.location.origin + "/complete/board?type=ADD";
        } else {
            let errorTag = document.querySelectorAll(".field-error");
            if (errorTag.length != 0) {
                errorTag.forEach(tag => tag.remove())
            }
            createErrorMsgHtml(data, false);
        }
    }).catch(err => alert(err));
}