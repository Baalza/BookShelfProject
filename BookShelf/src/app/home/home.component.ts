import { Component } from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'bookshelf-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  faUser=faUser;
}
