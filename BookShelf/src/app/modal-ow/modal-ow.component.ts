import {Component, Renderer2} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Params, Route, Router} from '@angular/router'
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'bookshelf-modal-ow',
  templateUrl: './modal-ow.component.html',
  styleUrls: ['./modal-ow.component.css']
})
export class ModalOwComponent {
  private modalReference: NgbActiveModal | undefined;
  constructor(private http: HttpClient, private router: Router) {
  }
  onSubmitOwFalse() {
    console.log('ciao');
      this.http.get('http://localhost:8080/demo/overwrite/false', {
        headers: {}
      })
        .pipe(
        ).subscribe((response: any) => {
        if (response === ("file-manager")) {
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/demo/file-manager']));
            //window.location.reload();
        }
      });
    }
  onSubmitOwTrue() {
    console.log('ciao');
    this.http.get('http://localhost:8080/demo/overwrite/true', {
      headers: {}
    })
      .pipe(
      ).subscribe((response: any) => {
      if (response === ("file-manager-ow-true")) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/demo/file-manager'], {queryParams: {ow: true}}));
      }
    });
  }


}

