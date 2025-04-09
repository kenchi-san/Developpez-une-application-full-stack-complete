import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'JWT_Token';  // Clé pour stocker le token dans le cookie
  private readonly apiUrl = `${environment.apiUrl}/auth`;  // URL de l'API (vous pouvez adapter selon votre API)

  constructor(private http: HttpClient) {}

  // Méthode de connexion
  login(userDetails: { username: string; password: string }): Observable<boolean> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, userDetails).pipe(
      map(response => {
        if (response && response.token) {
          // Si le token est présent dans la réponse, on le stocke dans un cookie sécurisé
          this.setTokenInCookie(response.token);
          return true;  // Connexion réussie
        } else {
          console.error('Réponse du serveur sans token', response);  // Débogage
          return false;  // Pas de token dans la réponse
        }
      }),
      catchError(error => {
        // Gérer les erreurs de connexion
        console.error('Erreur de connexion:', error);  // Afficher l'erreur pour déboguer
        return of(false);  // Retourner false en cas d'échec
      })
    );
  }

  // Méthode d'enregistrement
  register(userData: { fullName: string; email: string; password: string }): Observable<boolean> {
    return this.http.post<{ message: string }>(`${this.apiUrl}/register`, userData).pipe(
      map(response => {
        if (response && response.message) {
          console.log('Inscription réussie:', response.message);
          return true;  // Inscription réussie
        } else {
          console.error('Réponse du serveur sans message', response);  // Débogage
          return false;  // Pas de message de confirmation
        }
      }),
      catchError(error => {
        // Gérer les erreurs d'inscription
        console.error('Erreur lors de l\'inscription:', error);  // Afficher l'erreur pour déboguer
        return of(false);  // Retourner false en cas d'échec
      })
    );
  }

  // Méthode pour stocker le token dans un cookie sécurisé
  private setTokenInCookie(token: string): void {
    const expires = new Date();
    expires.setHours(expires.getHours() + 1); // Token valide pendant 1 heure
    document.cookie = `JWT_Token=${token}; Secure; HttpOnly; SameSite=Strict; path=/; expires=${expires.toUTCString()}`;
  }

  // Méthode de déconnexion
  logout(): void {
    document.cookie = 'JWT_Token=; Secure; HttpOnly; SameSite=Strict; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
  }

  // Récupérer le token depuis le cookie
  getToken(): string | null {
    const name = 'JWT_Token=';
    const decodedCookies = decodeURIComponent(document.cookie);
    const cookieArr = decodedCookies.split(';');

    for (let i = 0; i < cookieArr.length; i++) {
      let c = cookieArr[i].trim();
      if (c.indexOf(name) === 0) {
        return c.substring(name.length, c.length);
      }
    }
    return null;
  }

  // Vérifier si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    return !!this.getToken();  // Retourne true si un token est présent dans le cookie
  }
}
