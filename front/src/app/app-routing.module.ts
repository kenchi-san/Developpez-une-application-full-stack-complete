import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './guard/AuthGuard';
import {RegisterComponent} from "./pages/register/register.component";  // Assurez-vous que ce fichier existe

const routes: Routes = [
  { path: '', component: HomeComponent }, // Affiche directement la page d'accueil sans redirection
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent }, // à réactiver quand prêt
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  { path: '**', redirectTo: 'home' } // Si aucune route ne correspond, redirige vers la page d'accueil
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
