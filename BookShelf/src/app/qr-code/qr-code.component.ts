import {Component, OnInit, SecurityContext} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {LogoutService} from "../logout.service";





@Component({
  selector: 'bookshelf-qr-code',
  templateUrl: './qr-code.component.html',
  styleUrls: ['./qr-code.component.css']
})
export class QrCodeComponent implements OnInit{

  urlJ : String = "";
  public url: SafeUrl | null | undefined;


  constructor(private logoutService:LogoutService, private http: HttpClient,private router:Router, private route: ActivatedRoute, private domSanitizer: DomSanitizer) {
  }
  ngOnInit(): void {
    const options = { headers:{}, withCredentials : true };
    this.http.get('http://localhost:8080/demo/qrcode', options)
      .pipe(
      ).subscribe((response: any) => {

      if(!response.empty) {
        console.log(JSON.stringify(response));

        const urlJ = response.trim();
        this.url = this.domSanitizer.bypassSecurityTrustUrl(urlJ);
        console.log("url finale: "+this.url);

      }
      });
  }

  logout(){
    this.logoutService.logout();
  }


}
