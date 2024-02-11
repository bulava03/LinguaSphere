function createAnswerTableHeader(answerTable) {
    let answerHeaderRow = answerTable.insertRow();
    let answerHeaders = ["Текст відповіді", "Зображення", "Правильна", ""];
    let classesList = ['textCell', 'imageCell', 'checkboxCell', 'deleteCell'];
    let classIndex = 0;
    answerHeaders.forEach(headerText => {
        let th = document.createElement('th');
        th.textContent = headerText;
        th.classList.add(classesList[classIndex]);
        classIndex++;
        answerHeaderRow.appendChild(th);
    });
}

function createAnswerTableContentRow(answerTable, answer, index, correctAnswers) {
    let answerRow = answerTable.insertRow();
    let answerTextCell = answerRow.insertCell();
    answerTextCell.textContent = answer.text;
    answerTextCell.setAttribute("onclick", "displayTextAnswerForm(" + answer.id + ", '" + answer.text + "')");
    let answerImageCell = answerRow.insertCell();
    if (answer.file === null) {
        answerImageCell.setAttribute("onclick", "displayFileAnswerForm(" + answer.id + ", null)");
    } else {
        answerImageCell.setAttribute("onclick", "displayFileAnswerForm(" + answer.id + ", '" + answer.file + "')");
    }

    if (answer.file != null) {
        let answerImage = document.createElement('img');
        answerImage.src = 'data:image/jpeg;base64,' + answer.file;
        answerImage.alt = "Image";
        answerImage.width = 300;
        answerImage.height = 200;
        answerImageCell.appendChild(answerImage);
    } else {
        answerImageCell.textContent = 'Зображення відсутнє';
    }

    let correctCell = answerRow.insertCell();
    let correctCheckbox = document.createElement('input');
    correctCheckbox.type = "checkbox";
    correctCheckbox.checked = correctAnswers.includes(answer.id);
    correctCheckbox.id = 'answer' + answer.id;
    correctCheckbox.setAttribute("onclick", "changeAnswerCorrectness(" + answer.id + ", '" + correctCheckbox.id + "')");
    correctCell.appendChild(correctCheckbox);
    let deleteAnswerCell = answerRow.insertCell();
    deleteAnswerCell.textContent = "Видалити відповідь";
    deleteAnswerCell.setAttribute("onclick", "deleteAnswer(" + answer.id + ")");

    answerTextCell.classList.add('textCell');
    answerImageCell.classList.add('imageCell');
    correctCell.classList.add('checkboxCell');
    deleteAnswerCell.classList.add('deleteCell');
}

function createAnswerTable(questionId, value, correctAnswers) {
    var container = document.getElementById('testQuestionsContainer');
    var buttonElement = container.getElementsByTagName('button')[1];

    let answerTable = document.createElement('table');
    container.insertBefore(answerTable, buttonElement);
    answerTable.classList.add('contentTable');

    createAnswerTableHeader(answerTable);

    value.forEach((answer, index) => {
        createAnswerTableContentRow(answerTable, answer, index, correctAnswers);
    });

    let addAnswerRow = answerTable.insertRow();
    let addAnswerCell = addAnswerRow.insertCell();
    addAnswerCell.colSpan = 4;
    addAnswerCell.textContent = "Додати відповідь";
    addAnswerCell.classList.add('addAnswerCell');
    addAnswerCell.setAttribute("onclick", "addAnswer(" + questionId + ")");

    return answerTable;
}
