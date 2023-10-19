import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  constructor(private http: HttpClient, private userService: UserService) { }

  logout(){
    const options = { headers:{}, withCredentials : true };

    this.http.get('http://localhost:8080/demo/perform_logout', options)
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: "+JSON.stringify(response));
      if(response.error === false) {
        const cookieApp = response.cookie;
        var cookie =cookieApp.name+"="+cookieApp.value+"; expires=Thu, 18 Dec 2013 12:00:00 UTC; Path=/demo"+"; domain=localhost" ;
        this.userService.clearUser();
        localStorage.setItem('user', JSON.stringify(this.userService.getUser()));
        document.cookie = cookie;
        window.location.href = '/demo/';
      }
  });
}
}
