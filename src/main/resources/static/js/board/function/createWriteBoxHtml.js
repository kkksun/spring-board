const createWriteBoxHtml = (id, isEdit, comment) => {
    const editComment = isEdit ? comment : "";
    const div = document.createElement("div");
    div.setAttribute("class", "commentWriter");
    div.setAttribute("id", "writer"+id);
    let html = `<div class="commentInBox">
                       <div><em class="commentInBoxLoginId">${loginIdOfLoginMember}</em></div>
                       <textarea class="commentInBoxText" placeholder="댓글을 입력해주세요.">${editComment}</textarea>
                  </div>
                  <div class="commentAttach">`;
    if(isEdit) {
        html += `      <button onclick="editComment(${id})" type="button">등록</button>
                       <button onclick="reqEditCancel(${id})" type="button">취소</button>`
    } else {
        html += `      <button onclick="addComment(false,${id})" type="button">등록</button>
                      <button onclick="reqAddCancel(this)" data-id="${id}" type="button">취소</button>`
    }
    html += "</div>";

    div.innerHTML = html;
    return div;
}