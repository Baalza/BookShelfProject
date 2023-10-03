

const columnDefs = [
    { headerName: "Filename", field: "name", cellClass: "text-center", cellRenderer:filenameLinkRenderer },
    { headerName: "Date", field: "date",  cellClass: "justify-content-center"},
    {  headerName: 'Action',  headerClass: "justify-content-center",
        field: 'name', // Assumi che il campo nella tua row data sia 'filename'
        cellRenderer: customButtonRenderer,} // Usa la funzione del renderer personalizzato },

];

const gridOptions = {
    columnDefs: columnDefs,
    rowHeight: 50,
    onGridReady: (event) =>{renderDataInTheTable(event.api)}
};

const eGridDiv = document.getElementById('data-table');
new agGrid.Grid(eGridDiv, gridOptions);

function renderDataInTheTable(api) {
    fetch('http://localhost:8080/demo/restFiles')
        .then(function (response) {
            return response.json();

        }).then(function (data) {
        console.log(data);
        gridOptions.api.setRowData(data);
        api.sizeColumnsToFit();
    })
}

function customButtonRenderer(params) {
    const button = document.createElement('button');
    button.innerHTML = '<i class="fa-solid fa-trash"></i>';
    button.id = "trash";
    button.setAttribute("data-bs-toggle","modal");
    button.setAttribute("data-bs-target","#staticBackdrop2");
    button.classList.add('btn', 'btn-primary');
    button.type = 'button';

    // Recupera il valore del campo 'filename'
    const filename = params.data.name;
    button.setAttribute("data-rowid",filename);
    // Aggiungi l'evento onClick
    button.addEventListener('click', function () {
        // Esegui l'azione desiderata, utilizzando 'filename' se necessario
        tuaFunzioneDiEliminazione(filename);
    });

    return button;
}

function filenameLinkRenderer(params) {
    const filename = params.data.name; // Assume che il campo "name" contenga il nome del file

    const link = document.createElement('a');
    link.href = 'http://localhost:8080/demo/files/view/txt/' + filename; // Sostituisci 'url_del_file/' con l'URL del tuo servizio o endpoint per il download dei file
    link.textContent = filename;
    link.target = '_blank'; // Per aprire il link in una nuova scheda (opzionale)

    return link;
}
