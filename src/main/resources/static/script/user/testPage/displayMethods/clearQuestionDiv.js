function clearQuestionDiv() {
    const questionDiv = document.getElementById('questionDiv');

    while (questionDiv.firstChild) {
        questionDiv.removeChild(questionDiv.firstChild);
    }
}
