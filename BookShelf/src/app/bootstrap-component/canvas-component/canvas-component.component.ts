import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbOffcanvas, NgbOffcanvasOptions} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'bookshelf-canvas-component',
  templateUrl: './canvas-component.component.html',
  styleUrls: ['./canvas-component.component.css']
})
export class CanvasComponentComponent implements OnInit {
  username: String = "";
  company: String = "";

  constructor(private canvasService: NgbOffcanvas,private route: ActivatedRoute) {
  }
  @ViewChild('canvasElement', { static: true }) canvasElement!: ElementRef;
  ngOnInit(): void {
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
