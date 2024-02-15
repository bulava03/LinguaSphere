function displayQuestionMainPart(question) {
    const questionDiv = document.getElementById('questionDiv');

    if (question.text != null && question.text != '') {
        const questionText = document.createElement('h1');
        questionText.id = 'questionText';
        questionText.textContent = question.text;
        questionDiv.appendChild(questionText);
        const questionTextBr = document.createElement('br');
        questionTextBr.id = 'questionTextBr';
        questionDiv.appendChild(questionTextBr);
    }

    if (question.file != null) {
        const questionImage = document.createElement('img');
        questionImage.id = 'questionImage';
        questionImage.src = 'data:image/jpeg;base64,' + question.file;
        questionImage.alt = 'Image';
        questionImage.width = 600;
        questionImage.height = 340;
        questionDiv.appendChild(questionImage);
        const questionImageBr = document.createElement('br');
        questionImageBr.id = 'questionImageBr';
        questionDiv.appendChild(questionImageBr);
    }
}
