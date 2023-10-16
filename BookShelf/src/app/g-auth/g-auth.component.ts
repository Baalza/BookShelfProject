import { Component,OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Params, Route, Router} from '@angular/router'


@Component({
  selector: 'bookshelf-g-auth',
  templateUrl: './g-auth.component.html',
  styleUrls: ['./g-auth.component.css']
})
export class GAuthComponent implements OnInit{
  codeForm!: FormGroup;
  otpCode! : String;
  error: Boolean = false;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.initForm();

      this.route.queryParams.subscribe(params => {
      if (params['error'] === 'true') {
        console.log("error");
        this.error = true;
      }
    });
    }

  initForm(): void {
    this.codeForm = new FormGroup({
      otpCode: new FormControl(),
    });
  }
  onSubmit(){
    const code = this.codeForm.get('otpCode')?.value;

    const body = new FormData();
    const options = { headers:{}, withCredentials : true };
    body.append("otpCode",code);
    this.http.post('http://localhost:8080/demo/2fa-login', body, options)
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: "+JSON.stringify(response));
      var app = JSON.stringify(response);
      if(response === ("auth")) {
        console.log("sono in auth");
        var cookie ="Authenticated"+"="+"true"+"; Path=/demo"+"; domain=localhost" ;
        document.cookie=cookie;
        this.router.navigate(['']).then(() => {
          // Dopo il reindirizzamento, esegui il reload della pagina
          location.reload();
        });
      }else{
        console.log("no auth");
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/2fa-login'], {queryParams: {error: true}}));
      }
      });
  }
}
