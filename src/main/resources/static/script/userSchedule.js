function submitCell(dayValue, timeValue) {
    var cellSubmitDayInput = document.getElementById("cellSubmitDay");
    var cellSubmitTimeInput = document.getElementById("cellSubmitTime");
    cellSubmitDayInput.value = dayValue;
    cellSubmitTimeInput.value = timeValue;

    document.getElementById('formCellSubmit').submit();
}
