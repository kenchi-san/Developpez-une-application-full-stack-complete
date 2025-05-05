import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// Importation des composants standalone
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import {JwtInterceptor} from "./interceptor/JwtInterceptor";
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import {MatIconModule} from "@angular/material/icon";
import { CommentaireComponent } from './pages/commentaire/commentaire.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import {MatSelectModule} from "@angular/material/select";
import { ThemeListComponent } from './pages/theme-list/theme-list.component';
import { AccountComponent } from './pages/account/account.component';
import {CookieService} from "ngx-cookie-service";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ArticleListComponent,
    ArticleDetailComponent,
    CommentaireComponent,
    CreateArticleComponent,
    ThemeListComponent,
    AccountComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatCardModule,
    ReactiveFormsModule,
    MatInputModule,
    AppRoutingModule,

    LoginComponent,
    RegisterComponent,
    MatIconModule,
    FormsModule,
    MatSelectModule
  ],
  providers: [CookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {}
