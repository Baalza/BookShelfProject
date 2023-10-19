import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {faBars, faGear, faUser} from "@fortawesome/free-solid-svg-icons";
import {NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../user.service";

@Component({
  selector: 'bookshelf-footer-nav',
  templateUrl: './footer-nav.component.html',
  styleUrls: ['./footer-nav.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FooterNavComponent implements OnInit{

  protected readonly faBars = faBars;

  constructor(private canvasService: NgbOffcanvas,private userService:UserService) {
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

  role!: string;

  ngOnInit() {
    // Ottieni l'utente corrente
    const userJSON = localStorage.getItem('user');
    const user = userJSON ? JSON.parse(userJSON) : {};


    // Imposta la proprietà role
    this.role = user.role;
  }
}
