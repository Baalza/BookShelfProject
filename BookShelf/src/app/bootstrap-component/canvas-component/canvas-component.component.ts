import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {values} from "ag-grid-community/dist/lib/utils/generic";


@Component({
  selector: 'bookshelf-canvas-component',
  templateUrl: './canvas-component.component.html',
  styleUrls: ['./canvas-component.component.css']
})
export class CanvasComponentComponent implements OnInit {
  username: String = "";
  company: String = "";
  userForm!: FormGroup;
  name! : String;
  comp! : String;

  constructor(private canvasService: NgbOffcanvas,private route: ActivatedRoute,private http:HttpClient) {
  }
  @ViewChild('canvasElement', { static: true }) canvasElement!: ElementRef;
  ngOnInit(): void {
    this.initForm();
    this.route.queryParams.subscribe(params => {

      this.username = params['username']
      this.company = params['company']
      document.addEventListener('click', this.handleClickOutsideCanvas.bind(this));
    });
  }
  handleClickOutsideCanvas(event: MouseEvent) {
    // Verifica se il clic è avvenuto al di fuori del canvas
    if (!this.canvasElement.nativeElement.contains(event.target as Node)) {
      // Il clic è avvenuto al di fuori del canvas, ricarica la pagina
      window.history.replaceState({}, document.title, window.location.pathname);
      window.location.reload();
    }
  }
  initForm(): void {
    this.userForm = new FormGroup({
      //name: new FormControl(),
      name: new FormControl({ value: this.username, disabled: true }),
      company: new FormControl(this.company),
    });
  }
  public onSubmit() {
    const name = this.userForm.get('name')?.value;
    const company = this.userForm.get('company')?.value;

    console.log(name + " " + company);
    const body = new FormData();
    const options = { headers:{} };
    body.append("username", name);
    body.append("company", company);
    /*this.http.post('http://localhost:8080/demo/registration', body, options)
      .pipe(
      ).subscribe((response: any) => {
    });*/
    }
  public set(username: any, company: any) {
    console.log(username, company)
    this.username = username;
    this.company = company;
  }

  public close(): void {
    console.log("close")
    window.history.replaceState({}, document.title, window.location.pathname);
    window.location.reload()
    this.canvasService.dismiss();
  }


}
