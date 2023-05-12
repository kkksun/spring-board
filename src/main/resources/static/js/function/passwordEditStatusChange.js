let password;
let isPwChange = false;
let pwChangeBtn;

document.addEventListener("DOMContentLoaded", function (){
    password = document.getElementById("password");
    pwChangeBtn = document.getElementById("pwChangeBtn");

    if (pwChange) {
        password.style.display = "inline-block";
        isPwChange = true;
        pwChangeBtn.innerText = "취소";
    }
    findMember("edit");
});

const passwordChange = () => {

    if(password.style.display === "none") {
        password.style.display = "inline-block";
        isPwChange = true;
        pwChangeBtn.innerText = "취소";

    } else {
        password.style.display = "none";
        isPwChange = false;
        pwChangeBtn.innerText = "변경";
    }
}