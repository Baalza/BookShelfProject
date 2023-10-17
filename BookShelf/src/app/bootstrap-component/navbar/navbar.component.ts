import {Component, OnInit} from '@angular/core';
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {LogoutService} from "../../logout.service";
import {HttpClient} from "@angular/common/http";


@Component({
  selector: 'bookshelf-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  faUser = faUser;
  email: String = "";

  constructor(private logoutService: LogoutService, private http: HttpClient) {
  }

  isAuth: Boolean = false;


  performLogout() {
    console.log("logout");
    this.logoutService.logout();

  }

  ngOnInit(): void {

    if (document.cookie.split(';').find(cookie => cookie.trim().startsWith('Authenticated='))) {
      this.isAuth = true;
      const options = {headers: {}, withCredentials: true};
      this.http.get('http://localhost:8080/demo/restUser', options)
        .pipe(
        ).subscribe((response: any) => {
        console.log("risposta: " + JSON.stringify(response));
        this.email = response;
      });

    } else {
      this.isAuth = false;
    }
  }
}
