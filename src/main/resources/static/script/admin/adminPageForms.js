function submitAdminPageForm(action) {
    var form = document.getElementById('adminPageForm');
    form.action = action;
    form.submit();
}
