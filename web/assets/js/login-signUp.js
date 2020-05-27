const loginForm = document.querySelector('.login-form');
const signUpForm = document.querySelector('.sign-up-form');

document.body.onload = () => {
    loginForm.addEventListener('submit', event => {
        event.preventDefault();
        if (event.target.checkValidity() === false) {
            event.stopPropagation();
        } else {
            const requestData = new FormData(event.target);
            const url = 'login';

            postData(url, requestData).then(async response => {
                const responseData = await response.json();

                showModal(responseData.message, 1500);
            });
        }
        event.target.classList.add('was-validated');
    });

    signUpForm.addEventListener('submit', event => {
        event.preventDefault();
        if (event.target.checkValidity() === false) {
            event.stopPropagation();
        } else {
            const requestData = new FormData(event.target);
            const url = 'signUp';

            postData(url, requestData).then(async response => {
                const responseData = await response.json();

                showModal(responseData.message, 1500);
            });
        }
        event.target.classList.add('was-validated');
    });
};