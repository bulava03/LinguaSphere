function createTables(data) {
    Object.keys(data).forEach(key => {
        let element = data[key];
        key = JSON.parse(key);
        createQuestionTable(key);
        if (key.correctAnswers === null) {
            key.correctAnswers = [];
        }
        createAnswerTable(key.id, element, key.correctAnswers);
    });
}
