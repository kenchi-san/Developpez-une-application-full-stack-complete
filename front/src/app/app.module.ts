import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// Importation des composants standalone
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import {JwtInterceptor} from "./interceptor/JwtInterceptor";
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import {MatIconModule} from "@angular/material/icon"; // Importer ici, pas dans declarations

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ArticleListComponent,
    ArticleDetailComponent,
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
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,  // Utiliser l'intercepteur JWT
      multi: true,  // Permet de chaîner plusieurs intercepteurs
    }],
  bootstrap: [AppComponent]
})
export class AppModule {}
