function getGradingPage(gradeId) {
    var gradingInput = document.getElementById('gradeId');
    gradingInput.value = gradeId;
    var form = document.getElementById('getGradingPageForm');
    form.submit();
}

function submitGrade(points) {
    var gradingInput = document.getElementById('gradingInput');
    gradingInput.value = points;
    var form = document.getElementById('gradingForm');
    form.submit();
}
