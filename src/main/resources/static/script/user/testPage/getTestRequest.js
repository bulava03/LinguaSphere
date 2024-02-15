document.addEventListener('DOMContentLoaded', getTestQuestions());

function getTestQuestions() {
    const languageId = document.getElementById('questionDiv').getAttribute('languageId');

    fetch('/test/getTestQuestions?languageId=' + languageId)
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length === 0) {
                const questionDiv = document.querySelector('#questionDiv');
                var emptyQuestionsH1 = document.createElement('h1');
                emptyQuestionsH1.id = 'emptyQuestionsH1';
                emptyQuestionsH1.textContent = 'Питань до тестів із цієї мови на даний момент немає';
                questionDiv.appendChild(emptyQuestionsH1);
            } else {
                var dataArray = Object.entries(data);

                dataArray.sort((a, b) => {
                    var levelA = JSON.parse(a[0]).level;
                    var levelB = JSON.parse(b[0]).level;
                    return levelA > levelB ? 1 : (levelA < levelB ? -1 : 0);
                });

                var sortedData = {};
                dataArray.forEach(([key, value]) => {
                    sortedData[key] = value;
                });

                displayTestQuestions(sortedData);
            }
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}
