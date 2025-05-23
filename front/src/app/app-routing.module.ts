import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import {AuthRedirectGuard} from "./guard/AuthRedirectGuard";
import {AuthGuard} from "./guard/AuthGuard";
import {ArticleListComponent} from "./pages/article-list/article-list.component";
import {ArticleDetailComponent} from "./pages/article-detail/article-detail.component";
import {CreateArticleComponent} from "./pages/create-article/create-article.component";
import {ThemeListComponent} from "./pages/theme-list/theme-list.component";
import {AccountComponent} from "./pages/account/account.component";


const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthRedirectGuard] },

  { path: 'register', component: RegisterComponent, canActivate: [AuthRedirectGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard] },
  { path: 'me', component: AccountComponent, canActivate: [AuthGuard]  },

  { path: 'listArticle', component: ArticleListComponent, canActivate: [AuthGuard] },
  { path: 'listTheme', component: ThemeListComponent, canActivate: [AuthGuard] },
  { path:'article/detail/:articleId', component: ArticleDetailComponent, canActivate: [AuthGuard] },
  { path:'article/creation', component: CreateArticleComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'listArticle' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
