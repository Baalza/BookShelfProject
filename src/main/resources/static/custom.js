const tooltipTriggerList = document.querySelectorAll(
    '[data-bs-toggle="tooltip"]'
);
const tooltipList = [...tooltipTriggerList].map(
    (tooltipTriggerEl) => new bootstrap.Tooltip(tooltipTriggerEl)
);

$(document).on('click', '#trash', function() {
    var rowId = $(this).attr('data-rowid');
    $('#staticBackdrop2').find('.modal-title').html('Elimina: ' + rowId);

    $('#staticBackdrop2').find('.modal-body-txt').html('Sei sicuro di voler eliminare il file ' + rowId + '?');

    var deleteLink = document.getElementById("deleteFile");

    var currentUrl = deleteLink.getAttribute("href");


    var nuovoUrl = currentUrl + "" + rowId;

    deleteLink.setAttribute("href", nuovoUrl);

});

const fileInput = document.getElementById('formFile');
const submitButton = document.getElementById('submitFile');

fileInput.addEventListener('change', function () {
    if (fileInput.files.length > 0) {
        // Abilita il pulsante quando un file è stato selezionato
        submitButton.removeAttribute('disabled');
    } else {
        // Disabilita il pulsante se nessun file è stato selezionato
        submitButton.setAttribute('disabled', 'disabled');
    }
});

$(document).ready(function() {
    // Leggi il valore del parametro dalla URL
    var urlParams = new URLSearchParams(window.location.search);
    var varParam = urlParams.get('file');

    // Controlla se il parametro è "true" e apri la modal
    if (varParam === 'exist') {
        $('#staticBackdrop3').modal('show');
    }
});

$(document).ready(function(){
    var urlParams = new URLSearchParams(window.location.search);
    let type;
    let val;
// Itera attraverso tutti i parametri presenti nella query string
    urlParams.forEach(function (value, key) {
        type = key;
        val=value;
        console.log("Nome del parametro: " + key);
        console.log("Valore del parametro: " + value);
    });
    let typeB = document.getElementById('bisync');
    if(type != null) {
        typeB.href += '?' + type + '=' + val;
    }
});