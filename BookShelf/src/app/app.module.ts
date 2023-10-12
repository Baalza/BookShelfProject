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
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import { ModalOwComponent } from './modal-ow/modal-ow.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { TrashButtonComponent } from './trash-button/trash-button.component';
import { DeleteModalComponent } from './delete-modal/delete-modal.component';
import { LoginComponent } from './login/login.component';
import { GAuthComponent } from './g-auth/g-auth.component';




@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FileManagerComponent,
    NavbarComponent,
    DataGridFsComponent,
    DataGridGdComponent,
    ModalOwComponent,
    TrashButtonComponent,
    DeleteModalComponent,
    LoginComponent,
    GAuthComponent,
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        ToolbarComponent,
        FontAwesomeModule,
        AppRoutingModule,
        HttpClientModule,
        AgGridModule,
        NgbModule,
        ReactiveFormsModule,
        MatProgressSpinnerModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
