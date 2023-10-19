import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {FileManagerComponent} from "./file-manager/file-manager.component";
import {LoginComponent} from "./login/login.component";
import { authGuard } from './auth.guard';
import {preventLoginGuard} from "./prevent-login.guard";
import {GAuthComponent} from "./g-auth/g-auth.component";
import {QrCodeComponent} from "./qr-code/qr-code.component";
import {CreateRemoteComponent} from "./create-remote/create-remote.component";
import {RegistrazioneComponent} from "./registrazione/registrazione.component";
import {AccountConfirmationComponent} from "./account-confirmation/account-confirmation.component";
import {CreateCompanyComponent} from "./create-company/create-company.component";
import {UpdateUsersComponent} from "./update-users/update-users.component";

const routes: Routes = [
  {path: 'demo', component: HomeComponent, title: 'HOME - BookShelf'},
  {path: 'demo/account-confirmation', component: AccountConfirmationComponent, title: 'AccountConfirmation - BookShelf'},
  {path: 'demo/account-update', component: UpdateUsersComponent, title: 'UpdateUser - BookShelf'},
  {path: 'demo/company', component: CreateCompanyComponent, title: 'Company - BookShelf'},
  {path: 'demo/qr-code', component: QrCodeComponent, title: 'QRCODE - BookShelf'},
  {path: 'demo/registrazione', component: RegistrazioneComponent, title: 'Registrazione - BookShelf'},
  {path: 'demo/create-remote', component: CreateRemoteComponent, title: 'CreateRemote - BookShelf', canActivate: [authGuard]},
  {path: 'demo/file-manager', component: FileManagerComponent, title: 'FileManager - BookShelf',canActivate: [authGuard]},
  {path: 'demo/login', component: LoginComponent, title: 'Login - BookShelf',canActivate: [preventLoginGuard]},
  {path: 'demo/2fa-login', component: GAuthComponent, title: '2FA-LOGIN - BookShelf'},
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
