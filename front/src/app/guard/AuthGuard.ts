import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import {AuthService} from "../service/AuthService";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Vérifie si l'utilisateur est authentifié
    if (this.authService.isAuthenticated()) {
      return true;  // L'utilisateur peut accéder à la route
    } else {
      // Redirige l'utilisateur vers la page de connexion
      this.router.navigate(['/login'], {
        queryParams: { returnUrl: state.url } // On peut également transmettre l'URL de la page d'origine pour la redirection après authentification
      });
      return false;
    }
  }
}
