import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthService } from "../service/AuthService"; // Le service AuthService pour récupérer le token

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    // Vérifier si l'utilisateur est connecté et si le token existe
    const token = this.authService.getToken();

    if (token) {
      // Cloner la requête et ajouter l'en-tête Authorization avec le token
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Content-Type': "application/json",
          credentials: 'include',
        },

      });
    }
    // Passer la requête au prochain handler
    return next.handle(request);
  }
}
