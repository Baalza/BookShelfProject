import {ChangeDetectorRef, Component, ViewChild, ViewEncapsulation} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AgGridAngular } from 'ag-grid-angular';
import { CellClickedEvent, ColDef, GridReadyEvent } from 'ag-grid-community';
import { Observable } from 'rxjs';
import {TrashButtonComponent} from "../trash-button/trash-button.component";
import {
  NgbModule,
  NgbModal,
  NgbModalOptions,
  NgbActiveModal,
  NgbOffcanvas,
  NgbOffcanvasOptions
} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from '../delete-modal/delete-modal.component'
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";
import {CanvasComponentComponent} from "../bootstrap-component/canvas-component/canvas-component.component";
import {ModalOwComponent} from "../modal-ow/modal-ow.component";

@Component({
  selector: 'bookshelf-data-grid-user',
  templateUrl: './data-grid-user.component.html',
  styleUrls: ['./data-grid-user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DataGridUserComponent {

  component= CanvasComponentComponent;
  constructor(private canvasService: NgbOffcanvas, private http: HttpClient, private router: Router, private route: ActivatedRoute,private cd: ChangeDetectorRef, private comp: CanvasComponentComponent) {
  }

  canvasOption: NgbOffcanvasOptions = {
    position: 'bottom',
    panelClass: 'full'
  }

  public open(content: any): void {
    this.canvasService.open(content, this.canvasOption);
  }

public openC(event: { node: { data: any; }; }){
  if (event.node && event.node.data) {
    const selectedRowData = event.node.data;
    console.log(selectedRowData.id)
    //this.component.set?(selectedRowData.username,selectedRowData.company);
    //this.component?.set(selectedRowData.username,selectedRowData.company);
    this.open(this.component);
    const parametri: NavigationExtras = {
      queryParams: { username: selectedRowData.username, company: selectedRowData.company },
    };

    // Utilizza il servizio Router per navigare all'URL con i parametri
    this.router.navigate(['/demo/account-update'], parametri);

  }
}

  public close(): void {

    this.canvasService.dismiss();
  }

  onRowSelected(event:any) {
    if (event.node && event.node.data) {
      const selectedRowData = event.node.data;
      // Ora puoi chiamare il tuo metodo open e passare i dati della riga selezionata

    }
  }

  public columnDefs: ColDef[] = [
    { headerName: "#",field: 'id', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Nome",field: 'username', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
        if(valueA == valueB) return 0;
        return(valueA> valueB) ? 1 : -1;
      }},
    { headerName: "Azienda",field: 'company', comparator:(valueA,valueB,nodeA,nodeB, isDescending)=> {
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



  // Example load data from server
  onGridReady(params: GridReadyEvent) {
    const options = { headers:{}, withCredentials : true };
    this.rowData$ = this.http
      .get<any[]>('http://localhost:8080/demo/restUser/not-admin',options);
  }

}
