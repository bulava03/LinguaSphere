function setLessonConfirm(time, day) {
    var lessonDayInput = document.getElementById("setLessonDay");
    var lessonTimeInput = document.getElementById("setLessonTime");
    lessonDayInput.value = day;
    lessonTimeInput.value = time;

    document.getElementById('setLessonForm').submit();
}

function removeLessonConfirm(time, day) {
    var lessonDayInput = document.getElementById("removeLessonDay");
    var lessonTimeInput = document.getElementById("removeLessonTime");
    lessonDayInput.value = day;
    lessonTimeInput.value = time;

    document.getElementById('removeLessonForm').submit();
}
