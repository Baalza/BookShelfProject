import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './mat-components/toolbar/toolbar.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FileManagerComponent } from './file-manager/file-manager.component';
import { NavbarComponent } from './bootstrap-component/navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http'
import { AgGridModule } from 'ag-grid-angular';
import { DataGridFsComponent } from './data-grid-fs/data-grid-fs.component';
import { DataGridGdComponent } from './data-grid-gd/data-grid-gd.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FileManagerComponent,
    NavbarComponent,
    DataGridFsComponent,
    DataGridGdComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ToolbarComponent,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule,
    AgGridModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
