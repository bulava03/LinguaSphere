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
    checkbox.checked = element.isAvailable;
    checkbox.id = 'checkBox' + element.id;
    checkbox.setAttribute("onclick", "changeAvailableStatus(" + element.id + ", '" + checkbox.id + "')");
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
