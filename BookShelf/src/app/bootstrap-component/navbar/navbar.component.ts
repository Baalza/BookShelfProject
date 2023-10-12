import { Component } from '@angular/core';
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {LogoutService} from "../../logout.service";

@Component({
  selector: 'bookshelf-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  faUser = faUser;
constructor(private logoutService:LogoutService) {
}
  performLogout(){
  console.log("logout");

  this.logoutService.logout();
  }
}
