function addInsideFileQuestionFormStyles(button) {
    button.style.borderRadius = '20px';
    button.style.height = '70px';
    button.style.width = '200px';
    button.style.display = 'block';
    button.style.padding = '30px';
    button.style.margin = '30px auto 0px';
    button.style.fontSize = '16px';
}

function displayFileQuestionForm(questionId, oldFileVar) {
    var formContainer = document.createElement('div');
    formContainer.classList.add('form-container');

    var oldFile;

    if (oldFileVar != null) {
        oldFile = document.createElement('img');
        oldFile.src = 'data:image/jpeg;base64,' + oldFileVar;
        oldFile.alt = "Image";
        oldFile.width = 300;
        oldFile.height = 200;
        oldFile.style.display = 'block';
        oldFile.style.margin = '0 auto 30px';
    } else {
        oldFile = document.createElement('h3');
        oldFile.style.color = 'red';
        oldFile.textContent = 'Зображення відсутнє';
    }

    formContainer.style.width = '500px';

    var input = document.createElement('input');
    input.type = 'file';
    input.id = 'fileQuestionInput';

    var button = document.createElement('button');
    button.textContent = 'Зберегти';
    button.setAttribute("onclick", "editQuestionFile(" + questionId + ")");

    var buttonDeleteFile = document.createElement('button');
    buttonDeleteFile.textContent = 'Видалити';
    buttonDeleteFile.setAttribute("onclick", "deleteQuestionFile(" + questionId + ")");

    formContainer.appendChild(oldFile);
    formContainer.appendChild(input);
    formContainer.appendChild(button);
    formContainer.appendChild(buttonDeleteFile);

    document.body.appendChild(formContainer);

    var overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    addInsideFileQuestionFormStyles(button);
    addInsideFileQuestionFormStyles(buttonDeleteFile);
    darkenScreenWithFile(overlay, formContainer, button, buttonDeleteFile);
}
