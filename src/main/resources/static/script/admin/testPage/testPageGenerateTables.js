function createQuestionTableHeader(questionTable) {
    let questionHeaderRow = questionTable.insertRow();
    let questionHeaders = ['Текст питання', 'Зображення', 'Рівень', 'Доступне', ''];
    let classesList = ['textCell', 'imageCell', 'levelCell', 'checkboxCell', 'deleteCell'];
    let classIndex = 0;
    questionHeaders.forEach(headerText => {
        let th = document.createElement('th');
        th.textContent = headerText;
        th.classList.add(classesList[classIndex]);
        classIndex++;
        questionHeaderRow.appendChild(th);
    });
}

function createQuestionTable(element) {
    var container = document.getElementById('testQuestionsContainer');
    var buttonElement = container.getElementsByTagName('button')[1];

    let questionTable = document.createElement('table');
    container.insertBefore(questionTable, buttonElement);
    questionTable.classList.add('contentTable');

    createQuestionTableHeader(questionTable);

    let questionRow = questionTable.insertRow();
    let textCell = questionRow.insertCell();
    textCell.textContent = element.text;
    textCell.setAttribute("onclick", "displayTextQuestionForm(" + element.id + ", '" + element.text + "')");
    let imageCell = questionRow.insertCell();
    if (element.file === null) {
        imageCell.setAttribute("onclick", "displayFileQuestionForm(" + element.id + ", null)");
    } else {
        imageCell.setAttribute("onclick", "displayFileQuestionForm(" + element.id + ", '" + element.file + "')");
    }

    if (element.file != null) {
        let image = document.createElement('img');
        image.src = 'data:image/jpeg;base64,' + element.file;
        image.alt = "Image";
        image.width = 300;
        image.height = 200;
        imageCell.appendChild(image);
    } else {
        imageCell.textContent = 'Зображення відсутнє';
    }

    const levels = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2'];
    let levelCell = questionRow.insertCell();
    levelCell.textContent = levels[element.level - 1];
    levelCell.setAttribute("onclick", "displayLevelQuestionForm(" + element.id + ", " + element.level + ")");

    let checkboxCell = questionRow.insertCell();
    let checkbox = document.createElement('input');
    checkbox.type = "checkbox";
    checkbox.checked = false;
    checkboxCell.appendChild(checkbox);
    let deleteCell = questionRow.insertCell();
    deleteCell.textContent = "Видалити питання";
    deleteCell.setAttribute("onclick", "deleteQuestion(" + element.id + ")");

    textCell.classList.add('textCell');
    imageCell.classList.add('imageCell');
    checkboxCell.classList.add('checkboxCell');
    deleteCell.classList.add('deleteCell');
    levelCell.classList.add('levelCell');

    return questionTable;
}

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

function createAnswerTableContentRow(answerTable, answer, index, correctAnswer) {
    let answerRow = answerTable.insertRow();
    let answerTextCell = answerRow.insertCell();
    answerTextCell.textContent = answer.text;
    let answerImageCell = answerRow.insertCell();

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
    correctCheckbox.checked = (index + 1 === correctAnswer);
    correctCell.appendChild(correctCheckbox);
    let deleteAnswerCell = answerRow.insertCell();
    deleteAnswerCell.textContent = "Видалити відповідь";
    deleteAnswerCell.setAttribute("onclick", "deleteAnswer(" + answer.id + ")");

    answerTextCell.classList.add('textCell');
    answerImageCell.classList.add('imageCell');
    correctCell.classList.add('checkboxCell');
    deleteAnswerCell.classList.add('deleteCell');
}

function createAnswerTable(questionId, value, correctAnswer) {
    var container = document.getElementById('testQuestionsContainer');
    var buttonElement = container.getElementsByTagName('button')[1];

    let answerTable = document.createElement('table');
    container.insertBefore(answerTable, buttonElement);
    answerTable.classList.add('contentTable');

    createAnswerTableHeader(answerTable);

    value.forEach((answer, index) => {
        createAnswerTableContentRow(answerTable, answer, index, correctAnswer);
    });

    let addAnswerRow = answerTable.insertRow();
    let addAnswerCell = addAnswerRow.insertCell();
    addAnswerCell.colSpan = 4;
    addAnswerCell.textContent = "Додати відповідь";
    addAnswerCell.classList.add('addAnswerCell');
    addAnswerCell.setAttribute("onclick", "addAnswer(" + questionId + ")");

    return answerTable;
}

function createTables(data) {
    Object.keys(data).forEach(key => {
        let element = data[key];
        key = parseStringToObject(key);
        createQuestionTable(key);
        createAnswerTable(key.id, element, key.correctAnswer);
    });
}
