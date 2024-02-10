function addInsideLevelFormStyles(selectElement, button) {
    selectElement.style.borderRadius = '20px';
    selectElement.style.height = '80px';
    selectElement.style.width = '100px';
    selectElement.style.display = 'block';
    selectElement.style.padding = '30px';
    selectElement.style.margin = '30px auto 60px';

    button.style.borderRadius = '20px';
    button.style.height = '70px';
    button.style.width = '200px';
    button.style.display = 'block';
    button.style.padding = '30px';
    button.style.margin = '30px auto 30px';
    button.style.fontSize = '16px';
}

function displayLevelQuestionForm(questionId, level) {
    var formContainer = document.createElement('div');
    formContainer.classList.add('form-container');

    formContainer.style.width = '500px';

    var selectElement = document.createElement("select");
    var options = [
      { label: "A1", value: "1" },
      { label: "A2", value: "2" },
      { label: "B1", value: "3" },
      { label: "B2", value: "4" },
      { label: "C1", value: "5" },
      { label: "C2", value: "6" }
    ];
    options.forEach(function(option) {
      var optionElement = document.createElement("option");
      optionElement.textContent = option.label;
      optionElement.value = option.value;
      selectElement.appendChild(optionElement);
    });
    selectElement.id = 'levelQuestionArea';
    selectElement.value = level;

    var button = document.createElement('button');
    button.textContent = 'Зберегти';
    button.setAttribute("onclick", "editQuestionLevel(" + questionId + ")");

    formContainer.appendChild(selectElement);
    formContainer.appendChild(button);

    document.body.appendChild(formContainer);

    var overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    addInsideLevelFormStyles(selectElement, button);
    darkenScreen(overlay, formContainer, button);
}
