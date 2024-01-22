function updateMaterialConfirm(id) {
    document.getElementById("idUpdate").value = id;
    document.getElementById("updateForm").submit();
}

function removeMaterialConfirm(id) {
    document.getElementById("idRemove").value = id;
    document.getElementById("removeForm").submit();
}
