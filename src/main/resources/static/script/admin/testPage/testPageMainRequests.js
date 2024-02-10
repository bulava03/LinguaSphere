function addQuestion() {
    const languageId = document.getElementById('testQuestionsContainer').getAttribute('languageId');

    const url = '/test/addQuestion?languageId=' + languageId;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            clearTestQuestionsContainer();
            getTestQuestions();
        }
    })
    .catch(error => {
        console.error('There was a problem with adding question operation.');
    });
}

function deleteQuestion(questionId) {
    const languageId = document.getElementById('testQuestionsContainer').getAttribute('languageId');

    const url = '/test/deleteQuestion?questionId=' + questionId;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            clearTestQuestionsContainer();
            getTestQuestions();
        }
    })
    .catch(error => {
        console.error('There was a problem with delete question operation.');
    });
}

function addAnswer(questionId) {
    const url = '/test/addAnswer?questionId=' + questionId;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            clearTestQuestionsContainer();
            getTestQuestions();
        }
    })
    .catch(error => {
        console.error('There was a problem with adding answer operation.');
    });
}

function deleteAnswer(answerId) {
    const url = '/test/deleteAnswer?answerId=' + answerId;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            clearTestQuestionsContainer();
            getTestQuestions();
        }
    })
    .catch(error => {
        console.error('There was a problem with delete answer operation.');
    });
}
