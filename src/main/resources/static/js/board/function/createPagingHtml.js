const createPagingHtml = (pageParam) => {
    const pagingUi = document.getElementsByClassName("paging")[0];
    let html;

    html = `
              <li><a class="prev" href="/board?page=1"><span>&laquo;</span></a></li>
              <li><a class="prev" href="/board?page=${pageParam.prevPage}"><span>&lsaquo;</span></a></li>
            `

    for(let i = pageParam.startPage; i <= pageParam.endPage; i++) {
        html += `<li><a href="/board?page=${i}"`
        if(i == pageParam.currentPage) { html += " class=active "}
        html += `>${i}</a></li>`
    }

    html += `
              <li><a href="/board?page=${pageParam.nextPage}" class ="next"><span>&raquo;</span></a></li>
              <li><a href="/board?page=${pageParam.totalPage}" class="next"><span>&rsaquo;</span></a></li>
            `
    pagingUi.innerHTML= html;
}