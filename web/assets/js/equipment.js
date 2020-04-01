const searchButton = document.querySelector('#search-button');
const searchField = document.querySelector('#search');
const applyButton = document.querySelector('#apply');
const clearButton = document.querySelector('#clear');
let equipment = [];

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

document.body.onload = () => {
    const equipmentWrappers = document.querySelectorAll('.equipment-wrapper');
    const equipmentHeaders = document.querySelectorAll('.equipment__h2');
    const allBodyGroups = document.querySelectorAll('.equipment__li');
    for (let i = 0; i < equipmentWrappers.length; i++) {
        let bodyGroups = [];
        allBodyGroups.forEach(bodyGroup => {
            if (bodyGroup.getAttribute('data-parent') === equipmentHeaders[i].innerText) {
                bodyGroups.push(bodyGroup.innerText.toLowerCase());
            }
        });
        equipment.push(new Equipment(equipmentWrappers[i], equipmentHeaders[i], bodyGroups));
        console.log(`Equipment: ${equipment}`);
    }
};

searchButton.addEventListener('click', () => {
    clear();
    const searchingText = searchField.value;
    if (searchingText.length !== 0) {
        equipment.forEach(eq => {
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
       for (let eq of equipment) {
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
    equipment.forEach(eq => {
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