import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  constructor(private http: HttpClient) { }

  logout(){
    const options = { headers:{}, withCredentials : true };

    this.http.get('http://localhost:8080/demo/perform_logout', options)
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: "+JSON.stringify(response));
      if(response.error === false) {
        const cookieApp = response.cookie;
        var cookie =cookieApp.name+"="+cookieApp.value+"; expires=Thu, 18 Dec 2013 12:00:00 UTC; Path=/demo"+"; domain=localhost" ;
        document.cookie = cookie;
        //document.cookie = "username=John Doe; path=/demo, expires=Thu, 18 Dec 2024 12:00:00 UTC";



      }
  });
}
}
