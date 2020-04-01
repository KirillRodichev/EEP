const logo = document.querySelector('.navbar-brand__img');
const SMALL_LOGO = "30px";
const LARGE_LOGO = "60px";

window.onscroll = () => {
    document.documentElement.scrollTop > 80 ? logo.style.height = SMALL_LOGO : logo.style.height = LARGE_LOGO;
};