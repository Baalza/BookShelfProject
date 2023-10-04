import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {FileManagerComponent} from "./file-manager/file-manager.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent, title: 'HOME - BookShelf'},
  {path: 'file-manager', component: FileManagerComponent, title: 'FileManager - BookShelf'},
  {path: '',redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
