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
    if (this.authService.isAuthenticated()) {
      return true;  // L'utilisateur peut accéder à la route
    } else {
      // Redirige l'utilisateur vers la page de connexion avec l'URL d'origine comme paramètre de requête
      this.router.navigate([''], {
        queryParams: { returnUrl: state.url }  // Passe l'URL d'origine pour rediriger après authentification
      });
      return false;  // L'accès à la route est refusé
    }
  }

}
