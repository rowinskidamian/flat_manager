document.addEventListener("DOMContentLoaded", function () {
    let adressForm = document.getElementById('adress-details');
    adressForm.style.display = 'none';
    let showButton = document.getElementById('showAdress');

    showButton.addEventListener('click', function () {
        if (adressForm.style.display === 'none') {
            adressForm.style.display = 'block';
            showButton.innerHTML = 'Schowaj adres';
        } else {
            adressForm.style.display = 'none';
            showButton.innerHTML = 'Dodaj adres';
        }

    });

})