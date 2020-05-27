const fileValidation = (fileType, fileInput) => {
    const filePath = fileInput.value;
    let allowedExtensions;

    if (fileType === 'xml') {
        allowedExtensions = /(\.xml)$/i;
    } else if (fileType === 'img') {
        allowedExtensions = /(\.png|\.jpg|\.jpeg)$/i;
    } else {
        return true;
    }

    if (!allowedExtensions.exec(filePath)) {
        fileInput.value = '';
        return false;
    }

    return true;
};