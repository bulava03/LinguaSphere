function editPrice(languageId) {
    const table = document.getElementById('pricesTable');
    const teacherEmail = table.getAttribute('teacherEmail');
    const teacherPassword = table.getAttribute('teacherPassword');
    let newValue = document.getElementById('languagePriceInput').value;
    if (newValue === '') {
        newValue = 0;
    }

    if (!isNaN(newValue) && newValue > 0) {
        setNewPrice(teacherEmail, teacherPassword, languageId, newValue);
    } else {
        setNewPrice(teacherEmail, teacherPassword, languageId, 0.0);
    }
}
