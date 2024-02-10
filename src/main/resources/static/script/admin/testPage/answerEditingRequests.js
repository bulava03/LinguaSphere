function editAnswerText(answerId) {
    const textarea = document.getElementById('textAnswerArea');
    const newAnswerText = textarea.value;

    const url = '/test/editAnswerText?answerId=' + answerId + '&text=' + newAnswerText;

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
        console.error('There was a problem with editing answer text operation.');
    });
}

function editAnswerFile(answerId) {
    const input = document.getElementById('fileAnswerInput');
    const newAnswerFile = input.files[0];
    console.log(newAnswerFile);

    if (newAnswerFile != null) {
        const formData = new FormData();
        formData.append('answerId', answerId);
        formData.append('file', newAnswerFile);

        const url = '/test/editAnswerFile';

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
            console.error('There was a problem with adding answer file operation.');
        });
    }
}

function deleteAnswerFile(answerId) {
    const url = '/test/deleteAnswerFile?answerId=' + answerId;

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
        console.error('There was a problem with deleting answer file operation.');
    });
}
