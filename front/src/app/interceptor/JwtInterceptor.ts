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
import { AuthService } from "../service/AuthService"; // Ton service auth
import { Router } from "@angular/router";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        withCredentials: true
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Token expiré ou invalide → on déconnecte
          this.authService.logout();
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
