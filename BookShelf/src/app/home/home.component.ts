import {Component, OnInit} from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
@Component({
  selector: 'bookshelf-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements  OnInit{
  faUser=faUser;

  constructor(private modalService: NgbModal) {
  }
  ngOnInit(): void {
    close();
  }
  close(){
    this.modalService.dismissAll();
  }
}
