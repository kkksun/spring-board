const reqEditCancel = () => {
    const url = window.location.origin + (requestedPage === "MANAGE" ? "/manage" : "/member") + "/info/" + memberId;
    location.href = url;
};