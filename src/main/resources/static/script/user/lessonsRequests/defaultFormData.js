var token = document.querySelector('#defaultDataForm input[name="token"]').value;

var formData = {
  languageId: document.querySelector('#defaultDataForm input[name="languageId"]').value,
  teacherEmail: document.querySelector('#defaultDataForm input[name="teacherEmail"]').value,
  day: -1,
  time: -1,
};

const formToRemove = document.getElementById('defaultDataForm');
formToRemove.parentNode.removeChild(formToRemove);
