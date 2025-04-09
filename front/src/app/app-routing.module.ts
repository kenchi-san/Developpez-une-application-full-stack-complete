import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {AuthGuard} from "./guard/AuthGuard";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' }, // redirige vers home
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  // { path: 'products', component: ProductsComponent, canActivate: [AuthGuard] }, // à réactiver quand prêt
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
