import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Route, Router} from '@angular/router'

@Component({
  selector: 'bookshelf-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements  OnInit{
  userForm!: FormGroup;
  username! : String;
  password! : String;
  isInvalid: Boolean = false;
  constructor(private http: HttpClient,private router:Router, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.initForm();
    this.route.queryParams.subscribe(params => {
      if (params['invalid'] === 'true') {
        this.isInvalid = params['invalid'] === 'true';
      }
    });
  }
  initForm(): void {
    this.userForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    });
  }
  public onSubmit(){
    const username = this.userForm.get('username')?.value;
    const password = this.userForm.get('password')?.value;
    console.log(username+" "+password);
    const body = new FormData();
    body.append("username",username);
    body.append("password",password)
    this.http.post('http://localhost:8080/demo/perform_login', body,{
      headers: {}
    })
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: "+JSON.stringify(response));
      if(response.error === false) {
        window.location.replace("/home");

      }else if(response.error === "true"){

        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/login'], {queryParams: {invalid: true}}));
      }
    });
  }


}
