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
