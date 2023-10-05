import {Component} from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { faArrowsRotate } from '@fortawesome/free-solid-svg-icons';



@Component({
  selector: 'bookshelf-file-manager',
  templateUrl: './file-manager.component.html',
  styleUrls: ['./file-manager.component.css']
})
export class FileManagerComponent {
faUser = faUser;
faArrowsRotate = faArrowsRotate;


}
