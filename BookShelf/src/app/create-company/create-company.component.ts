import {ChangeDetectorRef, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {faGear, faUser} from "@fortawesome/free-solid-svg-icons";
import {NgbActiveModal, NgbModal, NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";
import {FormControl, FormGroup} from "@angular/forms";
import {ConfirmModalComponent} from "../confirm-modal/confirm-modal.component";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'bookshelf-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['./create-company.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateCompanyComponent implements OnInit {

  constructor(private canvasService: NgbOffcanvas, private http: HttpClient, private router: Router, private route: ActivatedRoute,private cd: ChangeDetectorRef) {
  }

  cpForm!: FormGroup;
  blank: Boolean = false;
  invalidPiva: Boolean = false;
  invalidEmail: Boolean = false;
  duplicatePiva: Boolean = false

  ngOnInit(): void {
    this.initForm();
    this.route.queryParams.subscribe(params => {
      if (params['blank'] === 'true') {
        this.blank = params['blank'] === 'true';
      } else if (params['invalidPiva'] === 'true') {
        this.invalidPiva = params['invalidPiva'] === 'true';
        this.cd.detectChanges();
        /*this.router.even((event: RouterEvent) => {
          // Eseguire un metodo quando cambia l'url
          this.myMethod();
        });*/
      } else if (params['invalidEmail'] === 'true') {
        this.invalidEmail = params['invalidEmail'] === 'true';
      } else if (params['duplicatePiva'] === 'true') {
        this.duplicatePiva = params['duplicatePiva'] === 'true';
      }
    });
  }

  initForm(): void {
    this.cpForm = new FormGroup({
      name: new FormControl(),
      piva: new FormControl(),
      telefono: new FormControl(),
      email: new FormControl(),
      url: new FormControl()
    });
  }

  public onSubmit() {
    const name = this.cpForm.get('name')?.value;
    const piva = this.cpForm.get('piva')?.value;
    const telefono = this.cpForm.get('telefono')?.value;
    const email = this.cpForm.get('email')?.value;
    const url = this.cpForm.get('url')?.value;
    console.log(name + " " + piva + " " + telefono + " " + email + " " + url);
    const body = new FormData();
    body.append("name", name);
    body.append("piva", piva);
    body.append("telefono", telefono);
    body.append("email", email);
    body.append("url", url);
    const options = {headers: {}, withCredentials: true};
    //body.append("username", username);

    this.http.post('http://localhost:8080/demo/createCompany', body, options)
      .pipe(
      ).subscribe((response: any) => {
      if (response === ("no-blank")) {
        this.duplicatePiva = false;
        this.invalidEmail = false;
        this.invalidPiva = false;
        this.blank = true;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/company'], {queryParams: {blank: true}}));
      } else if (response === ("invalid-piva")) {
        this.duplicatePiva = false;
        this.invalidEmail = false;
        this.invalidPiva = true;
        this.blank = false;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/company'], {queryParams: {invalidPiva: true}}));
      } else if (response === ("invalid-email")) {
        this.duplicatePiva = false;
        this.invalidEmail = true;
        this.invalidPiva = false;
        this.blank = false;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/company'], {queryParams: {invalidEmail: true}}));
      } else if (response === ("duplicate-piva")) {
        this.duplicatePiva = true;
        this.invalidEmail = false;
        this.invalidPiva = false;
        this.blank = false;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/company'], {queryParams: {duplicatePiva: true}}));
      } else if (response === ("add-company")) {
        console.log("ok")
        window.location.reload();
      }
    });
  }

  canvasOption: NgbOffcanvasOptions = {
    position: 'bottom',
    panelClass: 'full'
  }

  public open(content: any): void {
    this.canvasService.open(content, this.canvasOption);
  }

  public openIfErr(canv:any,err:boolean){
    this.open(canv);
  }

  public close(): void {
    this.canvasService.dismiss();
  }


}
