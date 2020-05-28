const loginForm = document.querySelector('.login-form');
const signUpForm = document.querySelector('.sign-up-form');

document.body.onload = () => {
    loginForm.addEventListener('submit', event => {
        //debugger
        event.preventDefault();
        if (event.target.checkValidity() === false) {
            event.stopPropagation();
        } else {
            const requestData = new FormData(event.target);
            const url = 'login';

            postData(url, requestData).then(async response => {
                const responseData = await response;
                debugger

                postDataRedirect('pages/cabinet.jsp', responseData);

                //showModal(responseData.message, 1500);
            }).catch(err => {
                console.log(`Failed to login. Reason: ${err.message}`);
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
            }).catch(err => {
                console.log(`Failed to login. Reason: ${err.message}`);
            });
        }
        event.target.classList.add('was-validated');
    });
};