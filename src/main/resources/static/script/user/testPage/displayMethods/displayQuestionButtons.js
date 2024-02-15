function displayQuestionButtons() {
    const questionDiv = document.getElementById('questionDiv');

    if (questionNumber != 0) {
        let backQuestionButton = document.createElement('button');
        backQuestionButton.id = 'backQuestionButton';
        backQuestionButton.innerText = 'Попереднє питання';
        backQuestionButton.setAttribute('onclick', 'backQuestion()');
        questionDiv.appendChild(backQuestionButton);
    }

    if (questionNumber != keys.length - 1) {
        let nextQuestionButton = document.createElement('button');
        nextQuestionButton.id = 'nextQuestionButton';
        nextQuestionButton.innerText = 'Наступне питання';
        nextQuestionButton.setAttribute('onclick', 'nextQuestion()');
        questionDiv.appendChild(nextQuestionButton);
    } else {
        let endTestButton = document.createElement('button');
        endTestButton.id = 'endTestButton';
        endTestButton.innerText = 'Завершити';
        endTestButton.setAttribute('onclick', 'endTest()');
        questionDiv.appendChild(endTestButton);
    }
}
