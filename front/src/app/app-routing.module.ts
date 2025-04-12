import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { IntraPageComponent } from './pages/intra-page/intra-page.component';
import {AuthRedirectGuard} from "./guard/AuthRedirectGuard";
import {AuthGuard} from "./guard/AuthGuard";  // Assurez-vous que ce fichier existe


const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthRedirectGuard] },  // Page d'accueil
  { path: 'home', component: HomeComponent, canActivate: [AuthRedirectGuard] },

  { path: 'register', component: RegisterComponent, canActivate: [AuthRedirectGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard] },

  { path: 'test', component: IntraPageComponent, canActivate: [AuthGuard] },

  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
