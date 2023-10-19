import {ChangeDetectorRef, Component, ViewEncapsulation} from '@angular/core';
import {NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'bookshelf-update-users',
  templateUrl: './update-users.component.html',
  styleUrls: ['./update-users.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UpdateUsersComponent {

  constructor(private canvasService: NgbOffcanvas, private http: HttpClient, private router: Router, private route: ActivatedRoute,private cd: ChangeDetectorRef) {
  }

  canvasOption: NgbOffcanvasOptions = {
    position: 'bottom',
    panelClass: 'full'
  }

  public open(content: any): void {
    this.canvasService.open(content, this.canvasOption);
  }



  public close(): void {
    this.canvasService.dismiss();
  }

}
