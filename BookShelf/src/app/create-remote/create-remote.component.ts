import { Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'bookshelf-create-remote',
  templateUrl: './create-remote.component.html',
  styleUrls: ['./create-remote.component.css']
})
export class CreateRemoteComponent implements OnInit{
  remoteForm!: FormGroup;
  name! : String;

  constructor(private http:HttpClient) {
  }
  ngOnInit(): void {
    this.initForm();
  }
  initForm(): void {
    this.remoteForm = new FormGroup({
      name: new FormControl()
    });
  }
  onSubmit(){
    const name = this.remoteForm.get('name')?.value;
    console.log(name);
    const body = new FormData();
    const options = { headers:{}, withCredentials : true };

    body.append("name",name);
    this.http.post('http://localhost:8080/demo/createRemote', body, options)
      .pipe(
      ).subscribe((response: any) => {
      console.log("risposta: " + JSON.stringify(response));
    });
  }
}
