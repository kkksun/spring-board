let html;
const createCommentListHtml = (isFirst, data) => {
    let commentList= document.getElementById("commentList");
    document.getElementsByClassName("commentInBoxText")[0].value="";
    document.getElementsByClassName("commentInBoxText")[0].style.height = "30px";
    if(isFirst) {
        commentList.innerHTML = "";
        html = "";
    }
    const padding = 20;
    const margin = "35px";

    data.forEach(d => {
        const commentPadding = padding * d["level"];
        const commentMargin = d["parentId"] != null ? margin : "0px";
        const addClass = d["parentId"] != null ?  ` pre${d["parentId"]}` : "" ;
        html += `<div class= "comment${addClass}" id = "comment${d["id"]}">
                      <div class="commentArea" style="padding-left: ${commentPadding}px">`
        if(d["parentId"] != null) {
            html += `        <img class="arrowIcon" src="/images/arrowIcon.png" >`;
        }
         html += `           <img class="userIcon" src="/images/userIcon.png" >
                            <div class="commentBox" style="margin-left: ${commentMargin}">
                                <div class="commentBox_loginId">${d["loginId"]}</div>
                                <div class="commentBox_comment">
                                    <p><span>${d["comment"]}</span></p>
                                </div>
                                <div class="commentInfoBox">`
             if(d["delCheck"] === "N") {
                 html += `           <span class="commentCreateDate">${d["createdDate"]}</span>
                                     <button onclick="reqAddComment(this)" data-id="${d["id"]}" data-parentid="${d["parentId"]}" type="button">답글</button>`
                 if (idOfLoginMember === d["memberId"] || typeOfLoginMember != "USER") {
                     html += `            <button onclick="reqEditComment(${d["id"]})" type="button">수정</button>
                                     <button onclick="deleteComment(${d["id"]})" type="button">삭제</button>`
                 }
             }
        html += `               </div>
                            </div>
                      </div>
                 </div>`

        if(d["childCommentList"].length != 0){
            createCommentListHtml(false, d["childCommentList"]);
        }
    });
    commentList.innerHTML = html;
}