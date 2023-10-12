import { Component,OnInit } from '@angular/core';
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {LogoutService} from "../../logout.service";


@Component({
  selector: 'bookshelf-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  faUser = faUser;
constructor(private logoutService:LogoutService) {
}
  isAuth: Boolean = false;


  performLogout(){
  console.log("logout");
  this.logoutService.logout();

  }

  ngOnInit(): void {

    if(document.cookie.split(';').find(cookie => cookie.trim().startsWith('Authenticated='))){
      this.isAuth = true;
    }else{
      this.isAuth = false;
    }
  }
}
