function updateLanguageConfirm(name) {
    document.getElementById("nameUpdate").value = name;
    document.getElementById("updateForm").submit();
}

function removeLanguageConfirm(name) {
    document.getElementById("nameRemove").value = name;
    document.getElementById("removeForm").submit();
}
