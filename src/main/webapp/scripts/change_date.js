document.addEventListener("DOMContentLoaded", function () {
    let startDateForm = document.getElementById('change-start-date');
    let endDateForm = document.getElementById('change-end-date');
    let changeDate = document.getElementById("changeDate");

    if (changeDate !== null) {
        startDateForm.style.display = 'none';
        endDateForm.style.display = 'none';
    }

    let changeButtonStart = document.getElementById('changeButtonStart');
    let changeButtonEnd = document.getElementById('changeButtonEnd');

    changeButtonStart.addEventListener('click', function () {
        if (startDateForm.style.display === 'none') {
            changeButtonStart.innerHTML = 'Anuluj zmianę';
            startDateForm.style.display = 'block';
        } else {
            changeButtonStart.innerHTML = 'Zmiana daty';
            startDateForm.style.display = 'none';
        }
    });

    changeButtonEnd.addEventListener('click', function () {
        if (endDateForm.style.display === 'none') {
            changeButtonEnd.innerHTML = 'Anuluj zmianę';
            endDateForm.style.display = 'block';
        } else {
            changeButtonEnd.innerHTML = 'Zmiana daty';
            endDateForm.style.display = 'none';
        }
    });
})