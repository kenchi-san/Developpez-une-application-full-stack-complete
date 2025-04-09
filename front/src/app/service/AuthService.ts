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
  private readonly apiUrl = `${environment.apiUrl}/auth/login`;  // L'URL de l'API de login

  constructor(private http: HttpClient) {}

  // Méthode de connexion
  login(userDetails: { username: string; password: string }): Observable<boolean> {
    return this.http.post<{ token: string }>(this.apiUrl, userDetails).pipe(
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

  // Méthode pour stocker le token dans un cookie sécurisé
  private setTokenInCookie(token: string): void {
    // Crée un cookie avec les options Secure, HttpOnly, et SameSite=Strict pour plus de sécurité
    const expires = new Date();
    expires.setHours(expires.getHours() + 1); // Token valide pendant 1 heure
    document.cookie = `JWT_Token=${token}; Secure; HttpOnly; SameSite=Strict; path=/; expires=${expires.toUTCString()}`;
  }

  // Méthode de déconnexion
  logout(): void {
    // Supprime le cookie lors de la déconnexion
    document.cookie = 'JWT_Token=; Secure; HttpOnly; SameSite=Strict; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
  }

  // Récupérer le token depuis le cookie
  getToken(): string | null {
    const name = 'JWT_Token=';
    const decodedCookies = decodeURIComponent(document.cookie);
    const cookieArr = decodedCookies.split(';');

    // Chercher le cookie avec le nom correspondant et le retourner
    for (let i = 0; i < cookieArr.length; i++) {
      let c = cookieArr[i].trim();  // Supprimer les espaces en début de chaîne
      if (c.indexOf(name) === 0) {
        return c.substring(name.length, c.length);  // Retourner le token
      }
    }
    return null;  // Aucun token trouvé
  }

  // Vérifier si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    return !!this.getToken();  // Retourne true si un token est présent dans le cookie
  }
}
