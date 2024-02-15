var questionNumber = 0;
var testPoints = [];
var keys = [];
var answers = [];

function displayTestQuestions(data) {
    Object.keys(data).forEach(key => {
            let element = data[key];
            key = JSON.parse(key);
            if (key.correctAnswers === undefined || key.correctAnswers === null) {
                key.correctAnswers = [];
            }
            keys.push(key);
            answers.push(element);
            testPoints.push([key.level, 0]);
        });

    displayQuestionMainPart(keys[0]);
    displayQuestionAnswers(answers[0], keys[0].correctAnswers);
    displayQuestionButtons();
}
