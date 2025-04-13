import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import {AuthRedirectGuard} from "./guard/AuthRedirectGuard";
import {AuthGuard} from "./guard/AuthGuard";
import {ArticleListComponent} from "./pages/article-list/article-list.component";
import {ArticleDetailComponent} from "./pages/article-detail/article-detail.component";


const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthRedirectGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthRedirectGuard] },

  { path: 'register', component: RegisterComponent, canActivate: [AuthRedirectGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard] },

  { path: 'listArticle', component: ArticleListComponent, canActivate: [AuthGuard] },
  { path:'article/detail/:articleId', component: ArticleDetailComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
