function addAccessConfirm(id) {
    document.getElementById("idAddAccess").value = id;
    document.getElementById("addAccessForm").submit();
}

function removeAccessConfirm(id) {
    document.getElementById("idRemoveAccess").value = id;
    document.getElementById("removeAccessForm").submit();
}
