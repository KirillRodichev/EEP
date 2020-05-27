const showModal = (message, timeout, body = null) => {
    const modalHeader = document.querySelector('#modalCenterTitle');
    const modalBody = document.querySelector('.modal-body');
    modalHeader.innerText = message;
    if (body) {
        if (modalBody.firstChild) {
            modalBody.removeChild(modalBody.firstChild);
        }
        modalBody.appendChild(body);
    }

    $('.modal').modal('show');

    if (timeout) {
        setTimeout(() => {
            $('.modal').modal('hide');
        }, timeout);
    }
};