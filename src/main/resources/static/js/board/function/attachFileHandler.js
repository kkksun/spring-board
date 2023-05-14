const attachFileHandler = () => {
    const fileInput = document.querySelector('#attachFileList');
    const newFileList = document.querySelector('#newFileList');
    const defaultText = document.querySelector('#default');
    let totalFiles = new Array() ;


    // 이전 파일 목록 삭제
    document.addEventListener('click', (e) => {
        if(e.target.className != 'btn_preRemove') return;
        const removeTargetId = e.target.dataset.fileid;
        preFileList = Array.from(preFileList.filter(fileId => fileId != removeTargetId));
        const removeTarget = document.getElementById(removeTargetId);
        removeTarget.remove();
        if(fileInput.files.length == 0 && preFileList.length == 0) {
            defaultText.innerText = '첨부파일';
        }
    });

    // 파일 등록 클릭 시 업로드한 파일 목록 저장
    document.addEventListener('click', (e) => {
        if(e.target.id != 'attachFileList') return;
        const preFiles = Array.from(fileInput.files);// 업로드한 파일 목록
        totalFiles = [];
        preFiles.forEach(file => totalFiles.push(file));
    })

    // 파일 선택하여 등록한 경우
    fileInput.addEventListener('change', () => {
        const newFiles = Array.from(fileInput.files); // 등록 시 파일을 수정할 때 새로 업로드한 파일
        const dataTransfer = new DataTransfer();

        // 새로 업로드한 파일 중 먼저 업로드한 파일과 중복이 된 파일 제외
        const addFileCount = newFiles.filter(newFile => !(totalFiles.some(file => newFile.lastModified == file.lastModified && newFile.name == file.name))).length;

        if((addFileCount + totalFiles.length + preFileList.length)  > 5) {
            alert("첨부 파일은 5개까지만 가능합니다.");
        } else {
            newFiles.filter(newFile => !(totalFiles.some(file => newFile.lastModified == file.lastModified && newFile.name == file.name))).forEach(file => totalFiles.push(file));
        }

        dataTransfer.items .clear();
        totalFiles.forEach(file => dataTransfer.items.add(file));
        fileInput.files = dataTransfer.files;

        if(totalFiles.length == 0 && preFileList.length == 0){defaultText.innerText = "첨부파일"} else {defaultText.innerText=""};
        newFileList.innerHTML = "";
        totalFiles.forEach(file => {
            newFileList.innerHTML += ` <div id="${file.lastModified}"> ${file.name} <button type="button" data-index="${file.lastModified}" class="btn_remove" >×</button></div>`;
        });
    });

    // 새로 등록한 파일에 대한 삭제 Handler
    document.addEventListener('click', (e) => {
        if(e.target.className != 'btn_remove') return;
        const removeTargetId = e.target.dataset.index;
        const removeTarget = document.getElementById(removeTargetId);
        const files = fileInput.files;
        const dataTransfer = new DataTransfer();
        Array.from(files)
            .filter(file => file.lastModified != removeTargetId)
            .forEach(file => {
                dataTransfer.items.add(file);
            });
        fileInput.files = dataTransfer.files;
        removeTarget.remove();
        if(fileInput.files.length == 0 && preFileList.length == 0) {
            defaultText.innerText = '첨부파일';
        }
    });
}