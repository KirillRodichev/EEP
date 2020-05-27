const equipmentSelector = document.querySelector('#equipment-selector');
const addSelectedButton = document.querySelector('#add-button');
const createBodyGroupsSelector = document.querySelector('#create-bodyGroups');
const bodyGroupsInput = document.querySelector('#bodyGroupsInput');
const checkBoxes = document.querySelectorAll('.form-check-input');
const equipmentCollapse = document.querySelector('.equipment-collapse');
const createForm = document.querySelector('#create-form');
const removeForms = document.querySelectorAll('.form-remove');
const deleteForms = document.querySelectorAll('.form-delete');
const updateForms = document.querySelectorAll('.update-form');
const modal = document.querySelector('.modal');
const downloadButton = document.querySelector('#download');
const uploadForm = document.querySelector('.upload-xml-form');

const gymID = document.querySelector('#gymID').value;

const XML_IMG_PATH = "assets/img/XML.jpg";

let selectedEquipmentID;

document.body.onload = () => {
    createBodyGroupsSelector.addEventListener('change', () => {
        const selectedBodyGroups = Array
            .from(createBodyGroupsSelector.options)
            .filter(option => option.selected)
            .map(option => option.value);
        bodyGroupsInput.value = JSON.stringify(selectedBodyGroups);
    });

    equipmentSelector.addEventListener('change', event => {
        selectedEquipmentID = Array.from(event.target.options).find(option => option.selected.value);
    });

    createForm.addEventListener('submit', event => {
        event.preventDefault();
        const fileInput = document.querySelector('.create-img-input');
        if (event.target.checkValidity() === false && !fileValidation('img', fileInput)) {
            event.stopPropagation();
        } else {
            equipmentCollapse.classList.remove('show');
            let formData = new FormData(createForm);
            formData.append('gymID', gymID);

            const url = 'createEquipment';
            postData(url, formData).then(() => {
                showModal('Created successfully!', 1500);
                applyFilters();
            }).catch(() => {
                console.log('Failed to create equipment!');
            });
        }
        createForm.classList.add('was-validated');
    });

    removeForms.forEach(form => {
        form.addEventListener('submit', event => {
            event.preventDefault();
            const formData = new FormData(event.target);
            const url = 'removeEquipment';

            postData(url, formData).then(() => {
                showModal('Removed successfully!', 1500);
                applyFilters();
            }).catch(() => {
                console.log('Failed to remove equipment!');
            });
        })
    });

    deleteForms.forEach(form => {
        form.addEventListener('submit', event => {
            event.preventDefault();
            const formData = new FormData(event.target);
            const url = 'deleteEquipment';

            postData(url, formData).then(() => {
                showModal('Deleted successfully!', 1500);
                applyFilters();
            }).catch(() => {
                console.log('Failed to delete equipment!');
            });
        })
    });

    downloadButton.addEventListener('click', () => {
        const url = 'XMLDownload';
        let requestData = new FormData();
        requestData.append('bgFilters', JSON.stringify(getCheckedBodyGroups()));
        requestData.append('gymID', gymID);

        printFormData(requestData);

        postData(url, requestData).then(async response => {
            const filePath = await response.json();
            const downloadLink = document.createElement('a');
            downloadLink.href = filePath;
            downloadLink.setAttribute('download', '');
            const image = document.createElement('img');
            image.src = XML_IMG_PATH;
            downloadLink.appendChild(image);

            showModal("Download XML", false, downloadLink);
        }).catch(err => {
            console.log('Failed to download file');
            console.log(err.message);
        });
    });

    uploadForm.addEventListener('submit', event => {
        event.preventDefault();
        const fileInput = document.querySelector('.upload-xml-input');

        if (event.target.checkValidity() === true && fileValidation('xml', fileInput)) {
            const url = 'XMLUpload';
            let requestData = new FormData(uploadForm);
            requestData.append('gymID', gymID);

            postData(url, requestData).then(() => {
                applyFilters();
                showModal('Uploaded successfully!', 1500);
            }).catch(err => {
                console.log(err.message);
            });
        } else {
            event.stopPropagation();
        }
        event.target.classList.add('was-validated');
    });

    updateForms.forEach(form => {
        form.addEventListener('submit', event => {

            event.preventDefault();
            const fileInput = event.target.querySelector('.custom-file-input');

            if (fileValidation('img', fileInput) || fileInput.value === '') {

                console.log('UPDATING EQ');

                const url = 'updateEquipment';
                const equipmentID = event.target.querySelector('.update.dispatch-attr.equipment-id').value;
                let requestData = new FormData(event.target);
                requestData.append('id', equipmentID);

                printFormData(requestData);

                postData(url, requestData).then(() => {
                    showModal('Updated successfully!', 1500);
                    applyFilters();
                }).catch(err => {
                    console.log('Failed to update equipment');
                    console.log(err.message);
                });

            } else {
                event.stopPropagation();
            }
            event.target.classList.add('was-validated');
        });
    });
};

addSelectedButton.addEventListener('click', () => {
    let requestData = new FormData();
    requestData.append('gymID', gymID);
    requestData.append('equipmentID', selectedEquipmentID);
    const url = 'addEquipment';

    postData(url, requestData).then(() => {
        applyFilters();
    }).catch(() => {
        console.log('Failed to create pagination!');
    });
});

const applyFilters = () => {
    const checkedBodyGroups = getCheckedBodyGroups();

    let requestData = new FormData();
    requestData.append('async', 'true');
    requestData.append('gymID', gymID);

    if (checkedBodyGroups.length > 0) {
        requestData.append('filters', JSON.stringify(checkedBodyGroups));
    }

    const url = 'equipment';
    postData(url, requestData).then(async response => {
        console.log('number');
        const [number, ...rest] = await response.json();
        console.log(number);
        createPagination(number);
        updateDomListener();
    }).catch(() => {
        console.log('Failed to create pagination!');
    });
};

const getCheckedBodyGroups = () => {
    let checkedBodyGroups = [];
    checkBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            checkedBodyGroups.push(checkBox.value.toLowerCase());
        }
    });
    return checkedBodyGroups;
};

const printFormData = data => {
    for (let [k, v] of data) {
        console.log(`${k} = ${v}`);
    }
};