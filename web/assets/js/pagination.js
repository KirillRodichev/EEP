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
            updateDomListener(event.target);
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

const updateDomListener = target => {
    let data = new FormData();
    data.append('async', 'true');

    data.append('gymID', gymID);

    if (target) {
        data.append('pageNumber', target.dataset.pageNumber);
        data.append('pageSize', target.dataset.pageSize);
    }

    const url = 'equipment';

    postData(
        url,
        data,
        target ? removeActiveClassName : () => {}
        ).then(async response => {

        const data = await response.json();

        console.log('UPDATE DOM');
        console.log(data);

        updateDOM(data);
    }).catch(() => {
        console.log('Failed to update dom');
    });

    if (target) addActiveClassName(target);
};
