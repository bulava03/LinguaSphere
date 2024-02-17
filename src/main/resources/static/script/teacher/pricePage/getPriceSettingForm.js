function addInsidePriceFormStyles(input, button) {
    input.style.borderRadius = '20px';
    input.style.height = '20px';
    input.style.width = '200px';
    input.style.display = 'block';
    input.style.padding = '30px';
    input.style.marginLeft = 'auto';
    input.style.marginRight = 'auto';
    input.style.fontSize = '32px';
    input.style.textAlign = 'center';

    button.style.borderRadius = '20px';
    button.style.height = '70px';
    button.style.width = '200px';
    button.style.display = 'block';
    button.style.padding = '30px';
    button.style.margin = '30px auto 0px';
    button.style.fontSize = '16px';
}

function displayPriceForm(languageId, oldPrice) {
    var formContainer = document.createElement('div');
    formContainer.classList.add('form-container');

    var input = document.createElement('input');
    input.setAttribute('placeholder', '0.0');
    if (oldPrice !== null) {
        input.value = oldPrice;
    }
    input.id = 'languagePriceInput';

    var errorTextElement = document.createElement('p');
    errorTextElement.style.color = 'red';
    errorTextElement.style.display = 'none';
    input.addEventListener('input', function () {
        var inputValue = input.value;
        if (isNaN(inputValue)) {
            errorTextElement.textContent = 'Ціна урока має бути числом';
            errorTextElement.style.display = 'inherit';
            button.disabled = true;
        } else if (inputValue <= 0.0 && inputValue !== '') {
            errorTextElement.textContent = 'Введено некоректне значення ціни урока';
            errorTextElement.style.display = 'inherit';
            button.disabled = true;
        } else {
            errorTextElement.textContent = '';
            errorTextElement.style.display = 'none';
            button.disabled = false;
        }
    });

    var button = document.createElement('button');
    button.textContent = 'Зберегти';
    button.setAttribute("onclick", "editPrice(" + languageId + ")");

    formContainer.appendChild(input);
    formContainer.appendChild(button);
    formContainer.appendChild(errorTextElement);

    document.body.appendChild(formContainer);

    var overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    addInsidePriceFormStyles(input, button);
    darkenScreen(overlay, formContainer, button);
}
