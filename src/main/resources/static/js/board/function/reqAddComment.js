const reqAddComment = (e) => {
    const commentId = e.dataset.id
    const parentTag = document.getElementById("comment"+commentId);
    const div = createWriteBoxHtml(commentId, false)
    parentTag.after(div);

    e.disabled = true;
}

const reqAddCancel = (e) => {
    const writerBox = document.getElementById("writer" + e.dataset.id);
    const commentDiv = document.getElementById("comment" + e.dataset.id);
    commentDiv.getElementsByTagName("button")[0].disabled = false;

    writerBox.remove();
}