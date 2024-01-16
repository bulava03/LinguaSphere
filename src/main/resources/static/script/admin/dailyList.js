function updateDailyConfirm(id) {
    document.getElementById("idUpdate").value = id;
    document.getElementById("updateForm").submit();
}

function removeDailyConfirm(id) {
    document.getElementById("idRemove").value = id;
    document.getElementById("removeForm").submit();
}
