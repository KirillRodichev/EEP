const searchButton = document.querySelector('#search-button');
const searchField = document.querySelector('#search');
const applyButton = document.querySelector('#apply');
const clearButton = document.querySelector('#clear');
const addButton = document.querySelector('#add-button');
const equipmentSelector = document.querySelector('#equipment-selector');
const equipmentIdInput = document.querySelector('#addedId');
let gymEquipment = [];
let allEquipmentNames = [];

class Equipment {
    constructor(wrapper, header, bodyGroups) {
        this._wrapper = wrapper;
        this._header = header;
        this._bodyGroups = bodyGroups;
    }
    get wrapper() {
        return this._wrapper;
    }
    get header() {
        return this._header.textContent.toLowerCase();
    }
    get bodyGroups() {
        return this._bodyGroups;
    }
    set display(val) {
        this._wrapper.style.display = val;
    }
    get display() {
        return this._wrapper.style.display;
    }
}

const loadEquipmentNames = (selectOptions) => {
    for (let option of selectOptions) {
        if (!allEquipmentNames.includes(option.value)) {
            allEquipmentNames.push(option.innerText);
        }
    }
};

const loadEquipment = (equipmentWrappers, allBodyGroups, equipmentHeaders) => {
    for (let i = 0; i < equipmentWrappers.length; i++) {
        let bodyGroups = [];
        allBodyGroups.forEach(bodyGroup => {
            if (bodyGroup.getAttribute('data-parent') === equipmentHeaders[i].innerText) {
                bodyGroups.push(bodyGroup.innerText.toLowerCase());
            }
        });
        gymEquipment.push(new Equipment(equipmentWrappers[i], equipmentHeaders[i], bodyGroups));
    }
};

document.body.onload = () => {
    const equipmentWrappers = document.querySelectorAll('.equipment-wrapper');
    const equipmentHeaders = document.querySelectorAll('.equipment__h2');
    const allBodyGroups = document.querySelectorAll('.equipment__li');
    const selectOptions = document.querySelectorAll('.add-select-option');
    loadEquipment(equipmentWrappers, allBodyGroups, equipmentHeaders);
    loadEquipmentNames(selectOptions);

    const loadedEquipmentNames = gymEquipment.map(eq => eq.header.trim());
    equipmentSelector.addEventListener('change', () => {
        equipmentIdInput.value = null;
        for (let option of selectOptions) {
            //debugger
            if (option.selected && !loadedEquipmentNames.includes(option.innerText.toLowerCase())) {
                equipmentIdInput.value = option.value;
            }
        }
    });
};

addButton.addEventListener('click', (e) => {
    e.preventDefault();
    if (equipmentIdInput.value) {
        //console.log(`equipmentIdInput.innerText = ${equipmentIdInput.innerText}`);
    }
});

searchButton.addEventListener('click', () => {
    clear();
    const searchingText = searchField.value;
    if (searchingText.length !== 0) {
        gymEquipment.forEach(eq => {
            if (!eq.header.includes(searchingText.toLowerCase())) {
                eq.display = 'none';
            }
        });
    }
});

applyButton.addEventListener('click', () => {
    clear();
   selectedChecksValues = [];
   const checkBoxes = document.querySelectorAll('.form-check-input');
   checkBoxes.forEach(checkBox => {
       if (checkBox.checked) {
           selectedChecksValues.push(checkBox.value.toLowerCase());
       }
   });
   if (selectedChecksValues.length !== 0) {
       for (let eq of gymEquipment) {
           for (let val of selectedChecksValues) {
               if (!eq.bodyGroups.includes(val)) {
                   eq.display = 'none';
                   break;
               }
           }
       }
   }
});

const clear = () => {
    gymEquipment.forEach(eq => {
        eq.display = 'block';
    });
};

const uncheck = () => {
    const checkBoxes = document.querySelectorAll('.form-check-input');
    checkBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            checkBox.checked = false;
        }
    });
};

clearButton.addEventListener('click', () => {
    clear();
    uncheck();
});