const eqImgPath = "assets/img/equipment/";
const gymLogoPath = "assets/img/gyms_logos/";

const eqWrapper = document.querySelectorAll('.equipment-wrapper');
const eqImages = document.querySelectorAll('.equipment__img');
const eqNameInputs = document.querySelectorAll('.input-name');
const eqDescriptionAreas = document.querySelectorAll('.textarea-description');
const eqBGListWrapper = document.querySelectorAll('.equipment-body-g__ul');
const eqUpdateEqIDs = document.querySelectorAll('.update.dispatch-attr.equipment-id');
const eqDeleteEqIDs = document.querySelectorAll('.delete.dispatch-attr.equipment-id');
const eqRemoveEqIDs = document.querySelectorAll('.remove.dispatch-attr.equipment-id');

const spanClassName = 'equipment-li__span';
const iClassName = ['fas', 'fa-circle', 'equipment-li__i'];
const bgLiClassName = 'equipment__li';

const gName = document.querySelector('.gym-name');
const gLogoPath = document.querySelector('.info-gym__img');
const gWebsiteURL = document.querySelector('.gym-website-url');
const gWebsite = document.querySelector('.gym-website');
const gPhone = document.querySelector('.gym-phone');
const gAddress = document.querySelector('.gym-address');
const gCity = document.querySelector('.gym-city');

const updateEquipmentDOM = data => {
    console.log("UPDATE");
    const [ size, ...equipment ] = data;
    console.log(equipment);
    console.log(size);
    for (let i = 0; i < 2; i++) {
        if (i < equipment.length) {
            eqWrapper[i].style.visibility = 'visible';
            const {id, name, description, imgPath, bodyGroups} = equipment[i];
            eqImages[i].src = eqImgPath + imgPath;
            eqNameInputs[i].value = name;
            eqDescriptionAreas[i].innerText = description;
            eqUpdateEqIDs[i].value = id;
            eqDeleteEqIDs[i].value = id;
            eqRemoveEqIDs[i].value = id;
            clearChildNode(eqBGListWrapper[i]);

            bodyGroups.forEach(bodyGroup => {
                const span = document.createElement('span');
                const circle = document.createElement('i');
                const li = document.createElement('li');
                span.classList.add(spanClassName);
                circle.classList.add(...iClassName);
                li.classList.add(bgLiClassName);

                span.appendChild(circle);
                li.innerText = bodyGroup;
                eqBGListWrapper[i].appendChild(span);
                eqBGListWrapper[i].appendChild(li);
            });
        } else {
            eqWrapper[i].style.visibility = 'hidden';
        }
    }
};

const updateCabinetDom = date => {
    console.log("UPDATE");
    const [city, ...gym] = date;
    console.log(city);
    console.log(gym);
    const [{name, logoPath, websiteURL, website, phone, address}] = gym;

    if (city)
        gCity.innerText = city;
    if (name)
        gName.innerText = name;
    if (websiteURL)
        gWebsiteURL.innerText = websiteURL;
    if (website)
        gWebsite.innerText = website;
    if (phone)
        gPhone.innerText = phone;
    if (address)
        gAddress.innerText = address;
    if (logoPath)
        gLogoPath.src = gymLogoPath + logoPath;
};

const clearChildNode = parent => {
    while (parent.lastElementChild) {
        parent.removeChild(parent.lastElementChild);
    }
};