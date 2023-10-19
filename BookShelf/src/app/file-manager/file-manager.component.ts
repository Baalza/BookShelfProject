import {Component, ElementRef, Renderer2, ViewChild, OnInit} from '@angular/core';
import {faUser} from '@fortawesome/free-solid-svg-icons';
import {faArrowsRotate} from '@fortawesome/free-solid-svg-icons';
import {NgbModule, NgbModal, NgbModalOptions, NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Params, Route, Router} from '@angular/router'
import {ModalOwComponent} from "../modal-ow/modal-ow.component";


@Component({
  selector: 'bookshelf-file-manager',
  templateUrl: './file-manager.component.html',
  styleUrls: ['./file-manager.component.css']

})
export class FileManagerComponent implements OnInit {
  faUser = faUser;
  faArrowsRotate = faArrowsRotate;
  private modalReference: NgbActiveModal | undefined;
  fileForm!: FormGroup;
  file!: File;
  isInvalid: Boolean = false;
  isOw: Boolean = false;
  component = ModalOwComponent;
  isLoading: Boolean = false;
  resp:any;

  @ViewChild('bisyncB', {static: true}) button!: ElementRef;


  constructor(private modalService: NgbModal, private http: HttpClient, private router: Router, private route: ActivatedRoute, private renderer: Renderer2) {
  }

  ngOnInit() {
    this.initForm();
    const options = { headers:{}, withCredentials : true };
    // Invia la chiamata POST
    this.http.get('http://localhost:8080/demo/restCompany/byRemote', options
    )
      .pipe(
      ).subscribe((response: any) => {
      console.log(response);
      this.resp = response;
    });
    this.route.queryParams.subscribe(params => {
      if (params['add'] === 'true' || params['ow'] === 'true' || params['delete'] === 'true') {
        // Se il parametro "add" è "true", aggiungi la classe al bottone
        // Puoi utilizzare il Renderer2 per modificare il DOM in modo sicuro
        this.renderer.addClass(this.button.nativeElement, 'btn-danger');
      } else {
        // Se il parametro "add" non è "true", rimuovi la classe dal bottone
        this.renderer.removeClass(this.button.nativeElement, 'btn-danger');
      }
      if (params['invalid'] === 'true') {
        this.isInvalid = params['invalid'] === 'true';
      } else if (params['exist'] === 'true') {
        console.log("open modal");
        this.open(this.component);
      } else {
        this.modalService.dismissAll(this.component);
      }


    });
  }

  initForm(): void {
    this.fileForm = new FormGroup({
      file: new FormControl('')
    });
  }

  onSubmit() {
    if (this.file !== undefined) {
      const body = new FormData();
      console.log("submit" + this.file)
      body.append("file", this.file);
      const options = { headers:{}, withCredentials : true };
      // Invia la chiamata POST
      this.http.post('http://localhost:8080/demo/loadFile', body, options

      )
        .pipe(
        ).subscribe((response: any) => {
        if (response === ("file-manager")) {
          this.close(['#staticBackdrop'])
          this.close(this.component);
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/demo/file-manager'], {queryParams: {add: true}}));
        } else if (response === ("file-manager-invalid")) {
          this.close(['#staticBackdrop']);
          this.close(this.component);
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/demo/file-manager'], {queryParams: {invalid: true}}));
        } else if (response === ("file-manager-ow")) {
          this.close(['#staticBackdrop']);
          this.close(this.component);
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/demo/file-manager'], {queryParams: {exist: true}}));
        }
      });
    }
  }

  bisync() {
    const queryParams = this.route.snapshot.queryParams;
    const paramName = Object.keys(queryParams)[0]; // Ottieni il nome del primo parametro
    const paramValue = queryParams[paramName];
    console.log("?" + paramName + paramValue);
    this.isLoading = true;
    this.http.get('http://localhost:8080/demo/bisync?' + paramName + "=" + paramValue, {
      headers: {}
    })
      .pipe(
      ).subscribe((response: any) => {
      if (response === ("file-manager")) {
        this.isLoading=false;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/file-manager']));
      }
    });
  }

  loadFile(event: any) {
    console.log("onChange" + event.target.files[0].name);
    if (event.target.files.length > 0) {
      console.log("if" + event.target.files[0].name);
      this.file = event.target.files[0];
      console.log(this.file);
    }
  }


  ngbModalOptions: NgbModalOptions = {
    backdrop: 'static',
    keyboard: false
  };

  public open(modal: any): void {
    this.modalReference = this.modalService.open(modal, this.ngbModalOptions);
  }

  public close(modal: any): void {
    // @ts-ignore
    this.modalReference.close();
  }

}
