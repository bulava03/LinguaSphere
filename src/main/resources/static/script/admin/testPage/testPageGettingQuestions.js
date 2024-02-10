document.addEventListener('DOMContentLoaded', getTestQuestions());

function getTestQuestions() {
    const languageId = document.getElementById('testQuestionsContainer').getAttribute('languageId');

    fetch('/test/getTestQuestions?languageId=' + languageId)
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length === 0) {
                var h1Element = document.querySelector('#testQuestionsContainer h1');
                h1Element.insertAdjacentHTML('afterend', '<h2>Питань до тестів із цієї мови на даний момент немає</h2>');
            } else {
                createTables(data);
            }
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

function clearTestQuestionsContainer() {
    var container = document.getElementById('testQuestionsContainer');
    var h1Element = container.getElementsByTagName('h1')[0];
    var buttonElement = container.getElementsByTagName('button')[1];

    var currentNode = h1Element.nextSibling;

    while (currentNode !== buttonElement) {
        var nextNode = currentNode.nextSibling;
        container.removeChild(currentNode);
        currentNode = nextNode;
    }
}
