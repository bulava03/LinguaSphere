function addInsideTextQuestionFormStyles(textarea, button) {
    textarea.style.borderRadius = '20px';
    textarea.style.height = '300px';
    textarea.style.width = '500px';
    textarea.style.display = 'block';
    textarea.style.padding = '30px';

    button.style.borderRadius = '20px';
    button.style.height = '70px';
    button.style.width = '200px';
    button.style.display = 'block';
    button.style.padding = '30px';
    button.style.margin = '30px auto 0px';
    button.style.fontSize = '16px';
}

function displayTextQuestionForm(questionId, areaText) {
    var formContainer = document.createElement('div');
    formContainer.classList.add('form-container');

    var textarea = document.createElement('textarea');
    textarea.setAttribute('placeholder', 'Введіть текст питання');
    textarea.value = areaText;
    textarea.id = 'textQuestionArea';

    var button = document.createElement('button');
    button.textContent = 'Зберегти';
    button.setAttribute("onclick", "editQuestionText(" + questionId + ")");

    formContainer.appendChild(textarea);
    formContainer.appendChild(button);

    document.body.appendChild(formContainer);

    var overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    addInsideFormStyles(textarea, button);
    darkenScreen(overlay, formContainer, button);
}
