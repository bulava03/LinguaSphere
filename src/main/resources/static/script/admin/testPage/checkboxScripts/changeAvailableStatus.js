function changeAvailableStatus(questionId, checkboxId) {
    let checkbox = document.getElementById(checkboxId);
    let isAvailable = !checkbox.checked;
    if (isAvailable) {
        setQuestionUnavailable(questionId);
    } else {
        setQuestionAvailable(questionId);
    }
}

function setQuestionAvailable(questionId) {
    const url = '/test/setQuestionAvailable?questionId=' + questionId;

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
        console.error('There was a problem with setting question available operation.');
    });
}

function setQuestionUnavailable(questionId) {
    const url = '/test/setQuestionUnavailable?questionId=' + questionId;

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
        console.error('There was a problem with setting question unavailable operation.');
    });
}
