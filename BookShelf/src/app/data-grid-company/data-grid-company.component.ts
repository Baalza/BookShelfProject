import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AgGridAngular } from 'ag-grid-angular';
import { CellClickedEvent, ColDef, GridReadyEvent } from 'ag-grid-community';
import { Observable } from 'rxjs';
import {TrashButtonComponent} from "../trash-button/trash-button.component";
import {NgbModule, NgbModal, NgbModalOptions, NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from '../delete-modal/delete-modal.component'

@Component({
  selector: 'bookshelf-data-grid-company',
  templateUrl: './data-grid-company.component.html',
  styleUrls: ['./data-grid-company.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DataGridCompanyComponent {

  public columnDefs: ColDef[] = [
    { headerName: "#",field: 'id', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Nome",field: 'name', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Partita Iva",field: 'pIva', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Telefono",field: 'tel', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Email",field: 'email', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "URL",field: 'url', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Action",field: '', cellRenderer: TrashButtonComponent,cellRendererParams: {
        clicked: (field: any) => {


        }
      },
    }
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

  constructor(private http: HttpClient,private modalService: NgbModal) {}

  // Example load data from server
  onGridReady(params: GridReadyEvent) {
    const options = { headers:{}, withCredentials : true };
    this.rowData$ = this.http
      .get<any[]>('http://localhost:8080/demo/restCompany',options);
  }

}
