function submitLanguage(languageId) {
    var languageIdInput = document.getElementById("languageIdInput");
    languageIdInput.value = languageId;

    document.getElementById('chooseLanguageForm').submit();
}
