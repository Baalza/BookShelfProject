import {Component, ViewEncapsulation} from '@angular/core';
import {faBars, faGear, faUser} from "@fortawesome/free-solid-svg-icons";
import {NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'bookshelf-footer-nav',
  templateUrl: './footer-nav.component.html',
  styleUrls: ['./footer-nav.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FooterNavComponent {

  protected readonly faBars = faBars;

  constructor(private canvasService: NgbOffcanvas) {
  }

  canvasOption: NgbOffcanvasOptions = {
    position: 'bottom',
    panelClass: 'auto'
  }

  public open(content: any): void {
    this.canvasService.open(content, this.canvasOption);
  }

  public close(): void {
    this.canvasService.dismiss();
  }

  protected readonly faUser = faUser;
  protected readonly faGear = faGear;
}
