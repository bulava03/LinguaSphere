function submitCellUsed(timeValue, dayValue) {
    var cellSubmitDayInput = document.getElementById("cellSubmitDayUsed");
    var cellSubmitTimeInput = document.getElementById("cellSubmitTimeUsed");
    cellSubmitDayInput.value = dayValue;
    cellSubmitTimeInput.value = timeValue;

    document.getElementById('formCellSubmitUsed').submit();
}

function submitCellFree(timeValue, dayValue) {
    var cellSubmitDayInput = document.getElementById("cellSubmitDayFree");
    var cellSubmitTimeInput = document.getElementById("cellSubmitTimeFree");
    cellSubmitDayInput.value = dayValue;
    cellSubmitTimeInput.value = timeValue;

    document.getElementById('formCellSubmitFree').submit();
}

function submitCellLesson(timeValue, dayValue) {
    var cellSubmitDayInput = document.getElementById("cellSubmitDayLesson");
    var cellSubmitTimeInput = document.getElementById("cellSubmitTimeLesson");
    cellSubmitDayInput.value = dayValue;
    cellSubmitTimeInput.value = timeValue;

    document.getElementById('formCellSubmitLesson').submit();
}
