const pagination = document.querySelector('.pagination');

const pgLiClassName = 'page-item';
const pgAClassName = 'page-link';

const createPagination = number => {
    console.log('CREATE PAGINATION');
    console.log(number);
    const pageNumber = Math.ceil(number / 2);
    clearChildNode(pagination);

    for (let i = 0; i < pageNumber; i++) {
        const li = document.createElement('li');
        const a = document.createElement('a');

        li.classList.add(pgLiClassName);
        a.classList.add(pgAClassName);
        a.innerText = (i + 1).toString(10);
        a.dataset.pageNumber = (i + 1).toString(10);
        a.dataset.pageSize = '2';

        a.addEventListener('click', event => {
            updateDomListener({target: event.target});
        });

        if (i === 0) a.classList.add('active');

        li.appendChild(a);
        pagination.appendChild(li);
    }
};

const addActiveClassName = element => {
    element.classList.add('active');
};

const removeActiveClassName = () => {
    Array.from(pagination.children).forEach(li => {
        if (li.firstChild.classList.contains('active')) {
            li.firstChild.classList.remove('active')
        }
    })
};

const updateDomListener = params => {
    const {target, reqData} = params;
    const url = 'equipment';
    let data;

    if (target) {
        data = new FormData();
        data.append('async', 'true');
        data.append('gymID', gymID);
        data.append('pageNumber', target.dataset.pageNumber);
        data.append('pageSize', target.dataset.pageSize);
    } else if (reqData) {
        data = reqData;
    }

    console.log('UPDATE PARAMS');
    for (let [k, v] of data) {
        console.log(`${k} = ${v}`);
    }

    postData(url, data, target ? removeActiveClassName : () => {}).then(async response => {

        const data = await response.json();

        console.log('UPDATE DOM');
        console.log(data);

        updateEquipmentDOM(data);
    }).catch(() => {
        console.log('Failed to update dom');
    });

    if (target) addActiveClassName(target);
};
