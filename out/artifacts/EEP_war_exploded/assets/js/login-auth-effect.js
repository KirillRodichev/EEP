let toLoginBtn = document.getElementById("toLogin");
let toSignUp = document.getElementById("toSignUp");
let wrapper = document.getElementById("wrapper");

toLoginBtn.onclick = () => {
    wrapper.classList.replace("box-left", "box-right");
};

toSignUp.onclick = () => {
    wrapper.classList.replace("box-right", "box-left");
};