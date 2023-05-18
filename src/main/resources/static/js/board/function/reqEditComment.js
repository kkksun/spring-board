const reqEditComment = (id) => {
    const comment = document.getElementById("comment" + id) ;
    const div = createWriteBoxHtml(id, true, comment.getElementsByTagName("span")[0].innerText);

    comment.before(div);
    comment.style.display="none";
}

const reqEditCancel = (id) => {
    document.getElementById("writer"+id).remove();
    document.getElementById("comment" + id).style.removeProperty("display");
}