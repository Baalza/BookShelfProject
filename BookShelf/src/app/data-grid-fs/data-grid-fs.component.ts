import { Component,ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AgGridAngular } from 'ag-grid-angular';
import { CellClickedEvent, ColDef, GridReadyEvent } from 'ag-grid-community';
import { Observable } from 'rxjs';
import {TrashButtonComponent} from "../trash-button/trash-button.component";
import {NgbModule, NgbModal, NgbModalOptions, NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from '../delete-modal/delete-modal.component'

@Component({
  selector: 'bookshelf-data-grid-fs',
  templateUrl: './data-grid-fs.component.html',
  styleUrls: ['./data-grid-fs.component.css']
})
export class DataGridFsComponent {
  /*components = {
    'trashButton': TrashButtonComponent
  };*/
  // Each Column Definition results in one Column.

  modalReference: NgbActiveModal | undefined;
  component = DeleteModalComponent;
  ngbModalOptions: NgbModalOptions = {
    backdrop: 'static',
    keyboard: false
  };

  public open(modal: any,field:any): void {
    const active = this.modalService.open(modal, this.ngbModalOptions);
    active.componentInstance.field = field;
  }
  public columnDefs: ColDef[] = [
    { headerName: "Filename",field: 'name', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Date",field: 'date',comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Action",field: 'name', cellRenderer: TrashButtonComponent,cellRendererParams: {
        clicked: (field: any) => {
          this.open(this.component,field);
          //alert(`${field} was clicked`);
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
      .get<any[]>('http://localhost:8080/demo/restFiles',options);
  }


}
