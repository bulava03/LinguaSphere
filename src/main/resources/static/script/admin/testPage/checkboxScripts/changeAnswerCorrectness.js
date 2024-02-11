function changeAnswerCorrectness(answerId, checkboxId) {
    let checkbox = document.getElementById(checkboxId);
    let isCorrect = !checkbox.checked;
    if (isCorrect) {
        setAnswerIncorrect(answerId);
    } else {
        setAnswerCorrect(answerId);
    }
}

function setAnswerCorrect(answerId) {
    const url = '/test/setAnswerCorrect?answerId=' + answerId;

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
        }
    })
    .catch(error => {
        console.error('There was a problem with setting answer correct operation.');
    });
}

function setAnswerIncorrect(answerId) {
    const url = '/test/setAnswerIncorrect?answerId=' + answerId;

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
        }
    })
    .catch(error => {
        console.error('There was a problem with setting answer incorrect operation.');
    });
}
