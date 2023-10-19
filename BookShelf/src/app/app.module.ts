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
import { QrCodeComponent } from './qr-code/qr-code.component';
import { CreateRemoteComponent } from './create-remote/create-remote.component';
import { RegistrazioneComponent } from './registrazione/registrazione.component';
import { ConfirmModalComponent } from './confirm-modal/confirm-modal.component';
import { AccountConfirmationComponent } from './account-confirmation/account-confirmation.component';
import {FooterNavComponent} from "./bootstrap-component/footer-nav/footer-nav.component";
import { CreateCompanyComponent } from './create-company/create-company.component';
import { DataGridCompanyComponent } from './data-grid-company/data-grid-company.component';
import { UpdateUsersComponent } from './update-users/update-users.component';
import { DataGridUserComponent } from './data-grid-user/data-grid-user.component';
import { CanvasComponentComponent } from './bootstrap-component/canvas-component/canvas-component.component';





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
    QrCodeComponent,
    CreateRemoteComponent,
    RegistrazioneComponent,
    ConfirmModalComponent,
    AccountConfirmationComponent,
    FooterNavComponent,
    CreateCompanyComponent,
    DataGridCompanyComponent,
    UpdateUsersComponent,
    DataGridUserComponent,
    CanvasComponentComponent,
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
  providers: [CanvasComponentComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
