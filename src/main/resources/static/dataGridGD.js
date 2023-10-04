const columnDefs1 = [
    { headerName: "Filename", field: "name", cellClass: "text-center"},
    { headerName: "Date", field: "date",  cellClass: "justify-content-center"},
   /* {  headerName: 'Action',  headerClass: "justify-content-center",
        field: 'name', // Assumi che il campo nella tua row data sia 'filename'
        cellRenderer: customButtonRenderer,} // Usa la funzione del renderer personalizzato }*/

];

const gridOptions1 = {
    columnDefs: columnDefs1,
    rowHeight: 50,
    onGridReady: (event) =>{renderDataInTheTable(event.api)}
};

const eGridDiv1 = document.getElementById('data-table-gd');
new agGrid.Grid(eGridDiv1, gridOptions1);

function renderDataInTheTable(api) {
    fetch('http://localhost:8080/demo/restFiles-drive')
        .then(function (response) {
            return response.json();

        }).then(function (data) {
        console.log(data);
        gridOptions1.api.setRowData(data);
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