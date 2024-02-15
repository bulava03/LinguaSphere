function countQuestionPoints() {
    let questionAnswers = keys[questionNumber].correctAnswers;
    if (questionAnswers.length === 1) {
        let radioButton = document.getElementById('answer_' + questionAnswers[0]);
        if (radioButton.checked) {
            testPoints[questionNumber] = 1;
        } else {
            testPoints[questionNumber] = 0;
        }
    } else {
        let checkboxes = document.querySelectorAll('input[type="checkbox"]');
        let questionPoints = 0;

        checkboxes.forEach(function(element) {
            var cleanedId = element.id.replace('answer_', '');
            if ((element.checked && questionAnswers.includes(parseInt(cleanedId))) ||
                (!element.checked && !questionAnswers.includes(parseInt(cleanedId)))) {
                questionPoints++;
            }
        });
        testPoints[questionNumber] = questionPoints;
    }
}
