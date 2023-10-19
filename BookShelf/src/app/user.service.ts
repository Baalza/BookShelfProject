import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private readonly http: HttpClient) {}


  // Aggiunta della proprietà user
  user!:  {
    email: "";
    role: "";
  };

  createUser(email: any,role: any) {
    this.user = {
      email: email,
      role: role
    };
  }

  getUser() {
    return this.user;
  }

  clearUser() {
    this.user = {
      email: "",
      role: "",
    };
  }

}
