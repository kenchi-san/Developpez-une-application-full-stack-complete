import {
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpEvent,
  HttpErrorResponse
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { AuthService } from "../service/AuthService";
import { Router } from "@angular/router";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    // Ajouter le token au header si présent
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur HTTP interceptée :', error);

        // Si erreur 403 => accès interdit (token invalide ou expiré)
        if (error.status === 403) {
          const currentRoute = this.router.url;

          // Éviter boucle infinie si déjà sur la page de login
          if (currentRoute !== '/login' && currentRoute !== '/') {
            this.authService.logout(); // Supprime les tokens
            this.router.navigate(['/']); // Redirige vers la page d'accueil ou login
          }
        }

        // Propager l’erreur pour gestion dans les composants
        return throwError(() => error);
      })
    );
  }

}
