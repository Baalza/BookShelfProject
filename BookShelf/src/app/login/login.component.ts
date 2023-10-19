import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Route, Router} from '@angular/router'
import {UserService} from "../user.service";

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
  url: String = "";
  constructor(private http: HttpClient,private router:Router, private route: ActivatedRoute,private readonly userService: UserService) {
  }
  ngOnInit(): void {
    this.url="https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A//www.googleapis.com/auth/drive.metadata.readonly&include_granted_scopes=true&response_type=token&state=state_parameter_passthrough_value&redirect_uri=http://localhost:4200/demo/&client_id=703748636020-f78st96be6le75udko9ssp0d3tvhkq27.apps.googleusercontent.com"
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
    const options = { headers:{}, withCredentials : true };

    body.append("username",username);
    body.append("password",password)
    this.http.post('http://localhost:8080/demo/perform_login', body, options)
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: "+JSON.stringify(response));
      if(response.error === false) {
        console.log(response['2fa']);
        if(response['2fa'] === false) {
          const cookieApp = response.cookie;
          var cookie =cookieApp.name+"="+cookieApp.value+"; Path=/demo"+"; domain=localhost" ;
          document.cookie = cookie;
          //il token authenticated lo creo solo se o non ha la 2fa oppure dopo che ha eseguito la 2fa
          //window.location.reload();
          //window.location.replace("");
          console.log(response.role);
          this.userService.createUser(response.email,response.role);
          const user = this.userService.getUser();
          localStorage.setItem('user', JSON.stringify(user));
          this.router.navigate(['']).then(() => {
            // Dopo il reindirizzamento, esegui il reload della pagina
             //location.reload();

          });
        }else if(response['2fa'] === true){
          this.router.navigate(['/demo/2fa-login']).then(() => {
            // Dopo il reindirizzamento, esegui il reload della pagina
            location.reload();

          });
        }
      }else if(response.error === true){

        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/login'], {queryParams: {invalid: true}}));
      }
    });
  }


}
