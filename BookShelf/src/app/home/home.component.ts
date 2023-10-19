import {Component, OnInit} from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LogoutService} from "../logout.service";

@Component({
  selector: 'bookshelf-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements  OnInit{
  faUser=faUser;

  constructor(private modalService: NgbModal,private logService: LogoutService) {
  }

  ngOnInit(): void {
    close();
    var self = this;
    /*window.addEventListener('beforeunload', function() {
      // Rimuovi i dati dal localStorage
      //self.logout();
      localStorage.removeItem('user');
    });*/
  }
  logout(){
    this.logService.logout();
  }
  close(){
    this.modalService.dismissAll();
  }



}
