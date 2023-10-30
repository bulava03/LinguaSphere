function dropdownUser() {
    document.getElementById('dropdown').classList.toggle('show');
}

window.onclick = function (event) {
    if (!event.target.matches('#userIcon')) {
        var dropdowns = document.getElementsByClassName('dropdown');
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function personalInformation() {
    document.getElementById('formPersonalInfo').submit();
}
