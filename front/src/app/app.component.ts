import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "./service/AuthService";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';
  excludedRoutes = ['/', '/login', '/register'];

  constructor(public router: Router,public authService: AuthService,) {}

  shouldShowNavbar(): boolean {
    return !this.excludedRoutes.includes(this.router.url);
  }
  logout(): void {
    this.authService.logout();
  }
}
