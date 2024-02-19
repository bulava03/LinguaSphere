function removeLanguageConfirm(teacherEmailValue) {
    var removeLanguageForm = document.getElementById('removeForm');
    var teacherEmailField = document.getElementById('teacherEmailRemove');
    teacherEmailField.value = teacherEmailValue;
    removeLanguageForm.submit();
}

function updateLanguageConfirm(teacherEmailValue) {
    var updateLanguageForm = document.getElementById('updateForm');
    var teacherEmailField = document.getElementById('teacherEmailUpdate');
    teacherEmailField.value = teacherEmailValue;
    updateLanguageForm.submit();
}
