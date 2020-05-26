postData = async (url, data, prepare = () => {}) => {
    spinLoader(true);
    prepare();

    //logFormData(data);

    const response = await fetch(url, {
        method: 'POST',
        body: data
    });
    spinLoader(false);
    return response;
};

const spinLoader = spin => {
    document.querySelector('#spinnerContainer').style.display = spin ? "block" : "none";
};

const logFormData = data => {
    if (data instanceof FormData) {
        for (let [k , v] of data) {
            console.log(`${k} = ${v}`);
        }
    }
};