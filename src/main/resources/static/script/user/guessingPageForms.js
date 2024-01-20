function submitPageForm() {
    var form = document.getElementById('userPageForm');
    form.submit();
}

function submitAnswerForm() {
    var inputVisible = document.getElementById('userText');
    var inputHidden = document.getElementById('answerText');
    inputHidden.value = inputVisible.value;
    var form = document.getElementById('creatureAnswerForm');
    form.submit();
}

function submitHintForm() {
    var form = document.getElementById('hintForm');
    form.submit();
}

function submitGiveUpForm() {
    var form = document.getElementById('giveUpForm');
    form.submit();
}
