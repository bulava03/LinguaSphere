function selectLessonConfirm(lessonId) {
    var lessonIdInput = document.getElementById("lessonId");
    lessonIdInput.value = lessonId;

    document.getElementById('lessonForm').submit();
}
