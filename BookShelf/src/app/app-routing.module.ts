import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {FileManagerComponent} from "./file-manager/file-manager.component";
import {LoginComponent} from "./login/login.component";
import { authGuard } from './auth.guard';
import {preventLoginGuard} from "./prevent-login.guard";
import {GAuthComponent} from "./g-auth/g-auth.component";

const routes: Routes = [
  {path: 'demo', component: HomeComponent, title: 'HOME - BookShelf'},
  {path: 'demo/file-manager', component: FileManagerComponent, title: 'FileManager - BookShelf',canActivate: [authGuard]},
  {path: 'demo/login', component: LoginComponent, title: 'Login - BookShelf',canActivate: [preventLoginGuard]},
  {path: 'demo/2fa-login', component: GAuthComponent, title: '2FALOGIN - BookShelf'},
  {path: '',redirectTo: '/demo', pathMatch: 'full'}
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
