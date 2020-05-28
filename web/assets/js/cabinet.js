const updateForm = document.querySelector('.update-form');

document.body.onload = () => {
  updateForm.addEventListener('submit', event => {
      debugger
      event.preventDefault();
      const logoInput = document.querySelector('#logoFile');

      if (fileValidation('img', logoInput.value) || logoInput.value === '') {
          const requestData = new FormData(event.target);
          const url = 'updateCabinetInfo';

          postData(url, requestData).then(async response => {
              const respData = await response.json();

              updateCabinetDom(respData);
          }).catch(err => {
              console.log('Failed to update cabinet info');
              console.log(err.message);
          })
      } else {
          // TODO validation not passed
      }
  });
};