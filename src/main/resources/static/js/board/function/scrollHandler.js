const scrollHandler = () => {
    // 내용 길이 Handler
    const content =  document.getElementsByClassName("content")[0];
    let height = Number(getComputedStyle(content).height.replace("px", ''));
    let padding = Number(getComputedStyle(content).paddingTop.replace("px", '')) +Number(getComputedStyle(content).paddingBottom.replace("px", ''));

    const DEFAULT_HEIGHT = height + padding;
    let currentScrollHeight = content.scrollHeight;

    if(currentScrollHeight > DEFAULT_HEIGHT) {
        content.style.height = scrollHeight + "px";
    }

    content.addEventListener('property-change', () => { scrollCalculation(); })
    content.addEventListener('change',() => { scrollCalculation(); })
    content.addEventListener('keyup', () => { scrollCalculation(); })
    content.addEventListener('paste', () => { scrollCalculation(); })
    content.addEventListener('input', () => { scrollCalculation(); })

    function scrollCalculation () {
        currentScrollHeight = content.scrollHeight;// e.target.scrollHeight;
        if(currentScrollHeight > DEFAULT_HEIGHT) {
            const position = currentScrollHeight - DEFAULT_HEIGHT;
            console.log(position)
            content.style.height = '0px';
            // content.style.height = '0px';
            // content.style.height = (currentScrollHeight + 12) + "px";
            content.style.height = (DEFAULT_HEIGHT + position + 12) + "px";
            console.log("현재 scroll 높이 = " + currentScrollHeight);
            console.log("text height = " + content.style.height);
        }
    }
}