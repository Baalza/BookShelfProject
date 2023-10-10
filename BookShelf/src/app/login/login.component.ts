import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'bookshelf-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements  OnInit{
  userForm!: FormGroup;
  username! : String;
  password! : String;
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.initForm();
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
    },
      (error: any) => {
        console.error('Errore durante la richiesta:', error);
      });
  }


}
