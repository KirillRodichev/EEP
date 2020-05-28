postData = async (url, data, prepare = () => {}) => {
    spinLoader(true);
    prepare();

    const response = await fetch(url, {
        method: 'POST',
        body: data
    });
    spinLoader(false);
    return response;
};

postDataRedirect = async (url, data, prepare = () => {}) => {
    spinLoader(true);
    prepare();

    const response = await fetch(url, {
        method: 'POST',
        body: data,
        redirect: 'follow'
    });
    spinLoader(false);
    return response;
};

const spinLoader = spin => {
    document.querySelector('#spinnerContainer').style.display = spin ? "block" : "none";
};