import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import {AuthService} from "../../service/AuthService";  // Importer MatSnackBar

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);  // Injection du service MatSnackBar

  isLoading = false;
  errorMessage: string | null = null;

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;  // Début du chargement
      this.errorMessage = null;  // Réinitialiser les erreurs

      // Appeler la méthode de login dans le service AuthService
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response) {
            this.router.navigate(['/home']);  // Redirige vers la page protégée après une connexion réussie
          } else {
            this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect.';
            this.snackBar.open(this.errorMessage, 'Fermer', { duration: 5000 });  // Afficher le message d'erreur avec MatSnackBar
          }
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors de la connexion. Veuillez réessayer plus tard.';
          this.snackBar.open(this.errorMessage, 'Fermer', { duration: 5000 });  // Afficher le message d'erreur avec MatSnackBar
        }
      });
    }
  }
}
