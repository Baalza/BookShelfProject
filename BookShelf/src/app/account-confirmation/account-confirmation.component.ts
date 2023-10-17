import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'bookshelf-account-confirmation',
  templateUrl: './account-confirmation.component.html',
  styleUrls: ['./account-confirmation.component.css']
})
export class AccountConfirmationComponent implements OnInit {
  invToken: Boolean = false;
  exToken: Boolean = false;
  confirm: Boolean = false;
  constructor(private http: HttpClient,private router:Router, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      console.log(token);
      const body = new FormData();
      const options = { headers:{} };

      body.append("token",token);
      this.http.get('http://localhost:8080/demo/accountConfirmation?token='+token)
        .pipe(
        ).subscribe((response: any) => {
          if(response === "invalid-token"){
            this.invToken = true;
          }else if(response === "expiry-token"){
            this.exToken = true;
          }else if(response === 'confirm'){
            this.confirm=true;
          }
      });
    });
  }

}
