function updateCreatureConfirm(id) {
    document.getElementById("idUpdate").value = id;
    document.getElementById("updateForm").submit();
}

function removeCreatureConfirm(id) {
    document.getElementById("idRemove").value = id;
    document.getElementById("removeForm").submit();
}
