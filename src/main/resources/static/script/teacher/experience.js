function updateExperience(experienceId) {
    var experienceIdInput = document.getElementById('experienceIdInput');
    experienceIdInput.value = experienceId;
    var form = document.getElementById('updateExperienceForm');
    form.submit();
}
