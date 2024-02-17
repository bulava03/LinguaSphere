function setNewPrice(teacherEmail, teacherPassword, languageId, newPrice) {
    const url = '/teacherPrices/setNewPrice?languageId=' + languageId +
                                            '&newPrice=' + newPrice +
                                            '&teacherEmail=' + teacherEmail +
                                            '&teacherPassword=' + teacherPassword;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            let languagePriceCell = document.getElementById('languageId_' + languageId);
            if (newPrice !== 0) {
                languagePriceCell.textContent = newPrice;
                languagePriceCell.setAttribute('onclick', 'displayPriceForm(' + languageId + ', ' + newPrice + ')');
            } else {
                languagePriceCell.textContent = 'Не встановлено';
                languagePriceCell.setAttribute('onclick', 'displayPriceForm(' + languageId + ', null)');
            }
        }
    })
    .catch(error => {
        console.error('There was a problem with setting new price operation.');
    });
}
