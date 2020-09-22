document.addEventListener("DOMContentLoaded", function () {
    let adressForm = document.getElementById('adress-details');
    let showButton = document.getElementById('showAdress');
    let addressDetailsInput = document.getElementById('validAddress');

    if(addressDetailsInput.value === "false") {
        adressForm.style.display = 'none';
    }

    if(addressDetailsInput.value === "true") {
        showButton.innerHTML = 'Schowaj adres';
    }

    showButton.addEventListener('click', function () {
        if (adressForm.style.display === 'none') {
            showButton.innerHTML = 'Schowaj adres';
            adressForm.style.display = 'block';
            addressDetailsInput.value = 'true';
        } else {
            showButton.innerHTML = 'Dodaj adres';
            adressForm.style.display = 'none';
            addressDetailsInput.value = 'false';
        }

    });

})