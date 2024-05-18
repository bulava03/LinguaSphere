function getCertificateAddingPage() {
    var form = document.getElementById('getGradingPageForm');
    form.submit();
}

function deleteCertificate(certificateId) {
    var certificateIdInput = document.getElementById('certificateIdInput');
    certificateIdInput.value = certificateId;
    var form = document.getElementById('deleteCertificateForm');
    form.submit();
}
