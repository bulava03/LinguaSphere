function editQuestionText(questionId) {
    const textarea = document.getElementById('textQuestionArea');
    const newQuestionText = textarea.value;

    const url = '/test/editQuestionText?questionId=' + questionId + '&text=' + newQuestionText;

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
        console.error('There was a problem with editing question text operation.');
    });
}

function editQuestionFile(questionId) {
    const input = document.getElementById('fileQuestionInput');
    const newQuestionFile = input.files[0];

    if (newQuestionFile != null) {
        const formData = new FormData();
        formData.append('questionId', questionId);
        formData.append('file', newQuestionFile);

        const url = '/test/editQuestionFile';

        fetch(url, {
            method: 'POST',
            body: formData
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
            console.error('There was a problem with adding question file operation.');
        });
    }
}

function deleteQuestionFile(questionId) {
    const url = '/test/deleteQuestionFile?questionId=' + questionId;

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
        console.error('There was a problem with deleting question file operation.');
    });
}

function editQuestionLevel(questionId) {
    const selectElement = document.getElementById('levelQuestionArea');
    const newQuestionLevel = selectElement.value;

    const url = '/test/editQuestionLevel?questionId=' + questionId + '&level=' + newQuestionLevel;

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
        console.error('There was a problem with editing question level operation.');
    });
}
