function backQuestion() {
    countQuestionPoints();
    clearQuestionDiv();
    questionNumber = questionNumber - 1;
    displayQuestionMainPart(keys[questionNumber]);
    displayQuestionAnswers(answers[questionNumber], keys[questionNumber].correctAnswers);
    displayQuestionButtons();
}

function nextQuestion() {
    countQuestionPoints();
    clearQuestionDiv();
    questionNumber = questionNumber + 1;
    displayQuestionMainPart(keys[questionNumber]);
    displayQuestionAnswers(answers[questionNumber], keys[questionNumber].correctAnswers);
    displayQuestionButtons();
}

function endTest() {
    countQuestionPoints();

    let testResult = countTestPoints();
    let color = '';
    if (testResult === 'None') {
        color = '#b03131';
    } else if (testResult === 'A1' || testResult === 'A2') {
        color = '#d16d15';
    } else if (testResult === 'B1' || testResult === 'B2') {
        color = '#c9a710';
    } else {
        color = '#39ad07';
    }

    clearQuestionDiv();
    displayTestResult(testResult, color);
}
