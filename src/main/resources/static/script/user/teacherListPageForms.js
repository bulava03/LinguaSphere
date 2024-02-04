function submitPageForm() {
    var form = document.getElementById('userPageForm');
    form.submit();
}

function submitTeacher(teacherEmail) {
    var form = document.getElementById('teacherScheduleForm');
    var teacherEmailInput = document.getElementById('teacherEmailInput');
    teacherEmailInput.value = teacherEmail;
    form.submit();
}
