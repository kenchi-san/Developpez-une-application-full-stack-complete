import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'JWT_Token';  // Clé pour stocker le token dans le cookie
  private readonly apiUrl = `${environment.apiUrl}/auth`;  // URL de l'API

  constructor(private http: HttpClient) {}

  // Méthode de connexion
  login(userDetails: { username: string; password: string }): Observable<boolean> {
    return this.http.post<{ token: string, expiresIn: number }>(`${this.apiUrl}/login`, userDetails).pipe(
      map(response => {
        if (response && response.token) {
          // Si le token est présent dans la réponse, on le stocke dans un cookie sécurisé
          this.setTokenInCookie(response.token, response.expiresIn);
          return true;  // Connexion réussie
        } else {
          console.error('Réponse du serveur sans token', response);
          return false;
        }
      }),
      catchError(error => {
        console.error('Erreur de connexion:', error);
        return of(false);  // Retourner false en cas d'échec
      })
    );
  }

  // Méthode d'inscription
  register(userData: { fullName: string; email: string; password: string }): Observable<boolean> {
    return this.http.post<{ message: string }>(`${this.apiUrl}/signup`, userData).pipe(
      map(response => {
        if (response && response.message) {
          console.log('Inscription réussie:', response.message);
          return true;
        } else {
          console.error('Réponse du serveur sans message', response);
          return false;
        }
      }),
      catchError(error => {
        console.error('Erreur lors de l\'inscription:', error);
        return throwError(() => error);  // Renvoi d'une erreur au composant
      })
    );
  }

  private setTokenInCookie(token: string, expiresIn: number): void {
    const expires = new Date();
    expires.setMilliseconds(expires.getMilliseconds() + (expiresIn * 1000));  // Convertir expiresIn en millisecondes

    const cookieAttributes = [
      `JWT_Token=${token}`,
      `Secure`,  // Le cookie est envoyé uniquement sur des connexions HTTPS
       // `HttpOnly`,  // Le cookie n'est pas accessible via JavaScript
      `SameSite=Strict`,  // Le cookie n'est envoyé que si la requête provient du même site
      `path=/`,  // Le cookie est disponible pour toutes les pages du site
      `expires=${expires.toUTCString()}`  // Définir la date d'expiration du cookie
    ].join('; ');
    console.log(cookieAttributes);

    // Assurer que le cookie est ajouté correctement
    document.cookie = cookieAttributes;
  }

  // Méthode de déconnexion
  logout(): void {
    // Supprimer le cookie en le réinitialisant avec une date d'expiration dans le passé
    document.cookie = 'JWT_Token=; Secure; HttpOnly; SameSite=Strict; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
  }

  // Récupérer le token depuis le cookie
  getToken(): string | null {
    const name = 'JWT_Token=';
    const decodedCookies = decodeURIComponent(document.cookie);  // Décoder les cookies
    const cookieArr = decodedCookies.split(';');  // Diviser les cookies en tableau

    for (let i = 0; i < cookieArr.length; i++) {
      let c = cookieArr[i].trim();
      if (c.indexOf(name) === 0) {
        return c.substring(name.length, c.length);  // Récupérer la valeur du token
      }
    }
    return null;  // Aucun token trouvé
  }

  // Vérifier si l'utilisateur est authentifié en vérifiant la présence du token dans le cookie
  isAuthenticated(): boolean {
    return !!this.getToken();  // Retourne true si un token est présent dans le cookie
  }
}
