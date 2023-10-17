import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ConfirmModalComponent} from "../confirm-modal/confirm-modal.component";


@Component({
  selector: 'bookshelf-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrls: ['./registrazione.component.css']
})
export class RegistrazioneComponent implements  OnInit{

  regForm!: FormGroup;
  username! : String;
  password! : String;
  rePassword! : String;
  blank: Boolean = false;
  invalidPw: Boolean = false;
  passMs: Boolean = false;
  invalidEm: Boolean = false;
  duplicate: Boolean = false;
  url: String = "";
  private modalReference: NgbActiveModal | undefined;
  component = ConfirmModalComponent;
  constructor(private modalService: NgbModal,private http: HttpClient,private router:Router, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.initForm();
    this.route.queryParams.subscribe(params => {
      if (params['blank'] === 'true') {
        this.blank = params['blank'] === 'true';
      }else if (params['invalidPw'] === 'true') {
        this.invalidPw = params['invalidPw'] === 'true';
      }else if (params['passMs'] === 'true') {
        this.passMs = params['passMs'] === 'true';
      }else if (params['invalidEm'] === 'true') {
        this.invalidEm = params['invalidEm'] === 'true';
      }else if (params['duplicate'] === 'true') {
        this.duplicate = params['duplicate'] === 'true';
      }else if(params['registered'] === 'true'){
        this.open(this.component)
        setTimeout(() => {
          window.location.href = '/demo/';

        },   3000);
      }
    });
  }
  initForm(): void {
    this.regForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl(),
      rePassword: new FormControl()
    });
  }
  public onSubmit() {
    const username = this.regForm.get('username')?.value;
    const password = this.regForm.get('password')?.value;
    const rePassword = this.regForm.get('rePassword')?.value;
    console.log(username + " " + password + " " + rePassword);
    const body = new FormData();
    const options = { headers:{} };
    body.append("username", username);
    body.append("password", password);
    body.append("rePassword", rePassword);
    this.http.post('http://localhost:8080/demo/registration', body, options)
      .pipe(
      ).subscribe((response: any) => {
      if (response === ("no-blank")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {blank: true}}));
      }else if (response === ("invalid-password")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {invalidPw: true}}));
      }else if (response === ("password-mismatch")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {passMs: true}}));
      }else if (response === ("invalid-email")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {invalidEm: true}}));
      }else if (response === ("duplicate-emai")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {duplicate: true}}));
      }else if (response === ("registered")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/registrazione'], {queryParams: {registered: true}}));
      }


    });
  }
  public open(modal: any): void {
    this.modalReference = this.modalService.open(modal);
  }
}
