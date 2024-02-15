function displayQuestionAnswers(answers, correctAnswers) {
    var table = document.createElement('table');
    table.id = 'answersTable'
    const questionDiv = document.getElementById('questionDiv');
    questionDiv.appendChild(table);

    answers.forEach(function(answer) {
        var row = table.insertRow();

        var buttonCell = row.insertCell(0);
        var button = document.createElement('input');
        if (correctAnswers.length === 1) {
            button.type = 'radio';
            button.name = 'radioButton';
            button.id = 'answer_' + answer.id;
        } else {
            button.id = 'answer_' + answer.id;
            button.type = 'checkbox';
        }
        buttonCell.appendChild(button);

        var answerCell = row.insertCell(1);
        var textTd = document.createElement('p');
        textTd.textContent = answer.text;
        answerCell.appendChild(textTd);

        if (answer.file) {
            var image = document.createElement('img');
            image.src = 'data:image/jpeg;base64,' + answer.file;
            image.width = 300;
            image.height = 170;
            answerCell.appendChild(image);
        }
    });
}
