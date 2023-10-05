import { Component, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AgGridAngular } from 'ag-grid-angular';
import { CellClickedEvent, ColDef, GridReadyEvent } from 'ag-grid-community';
import { Observable } from 'rxjs';
@Component({
  selector: 'bookshelf-data-grid-gd',
  templateUrl: './data-grid-gd.component.html',
  styleUrls: ['./data-grid-gd.component.css']
})
export class DataGridGdComponent {
  public columnDefs: ColDef[] = [
    { headerName: "Filename",field: 'name', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
      if(valueA == valueB) return 0;
      return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Date",field: 'date',comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
  ];

  // DefaultColDef sets props common to all Columns
  public defaultColDef: ColDef = {
    sortable: true,
    filter: true,
  };

  // Data that gets displayed in the grid
  public rowData$!: Observable<any[]>;

  // For accessing the Grid's API
  @ViewChild(AgGridAngular) agGrid!: AgGridAngular;

  constructor(private http: HttpClient) {}

  // Example load data from server
  onGridReady(params: GridReadyEvent) {
    this.rowData$ = this.http
      .get<any[]>('http://localhost:8080/demo/restFiles-drive');
  }

}
