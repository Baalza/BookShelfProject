import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Params, Route, Router} from '@angular/router'

@Component({
  selector: 'bookshelf-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent implements OnInit {

  field: any;
  private modalReference: NgbActiveModal | any;

  constructor(private modalService: NgbModal,private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
    // Get the field value from the parameters

    // Do something with the field value
  }

  public close(): void {
    // @ts-ignore
    this.modalService.dismissAll();
  }

  public onCLick(field: any) {
    console.log(field);


    this.http.get('http://localhost:8080/demo/deleteFile/'+field, {
      headers: {}
    })
      .pipe(
      ).subscribe((response: any) => {
      if (response === ("file-manager")) {
        this.close();
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
          this.router.navigate(['/demo/file-manager'], {queryParams: {delete: true}}));
      }
    });
  }
}
