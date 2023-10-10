import {Component, Input, OnInit} from '@angular/core';
import {faArrowsRotate, faTrash} from '@fortawesome/free-solid-svg-icons';
import {ICellRendererAngularComp} from "ag-grid-angular";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'bookshelf-trash-button',
  templateUrl: './trash-button.component.html',
  styleUrls: ['./trash-button.component.css']
})
export class TrashButtonComponent implements  ICellRendererAngularComp{

  faTrash = faTrash;
  params : any;

  constructor() {
  }

  btnClickedHandler() {
    this.params.clicked(this.params.value);
  }

  agInit(params: any): void {
    this.params = params;

  }

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }

}
