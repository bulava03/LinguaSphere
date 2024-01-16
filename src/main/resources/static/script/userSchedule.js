function submitCell(timeValue, dayValue) {
    var cellSubmitDayInput = document.getElementById("cellSubmitDay");
    var cellSubmitTimeInput = document.getElementById("cellSubmitTime");
    cellSubmitDayInput.value = dayValue;
    cellSubmitTimeInput.value = timeValue;

    document.getElementById('formCellSubmit').submit();
}

function submitLanguageChoosingForm() {
    var form = document.getElementById('choosingLanguageForm');
    form.submit();
}
