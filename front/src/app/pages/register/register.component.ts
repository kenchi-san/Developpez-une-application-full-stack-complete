import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../service/AuthService';
import { MatIconModule } from '@angular/material/icon';  // Importer MatIconModule pour les icônes
import { Location } from '@angular/common';  // Importer Location pour la navigation

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);
  private location = inject(Location);  // Injection du service Location

  isLoading = false;
  errorMessage: string | null = null;

  registerForm: FormGroup = this.fb.group({
    fullName: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  onSubmit() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      // Appeler le service d'inscription
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response) {
            this.router.navigate(['/login']); // Redirige vers la page de connexion après une inscription réussie
          } else {
            this.errorMessage = 'Erreur lors de l\'inscription. Veuillez réessayer plus tard.';
            this.snackBar.open(this.errorMessage, 'Fermer', { duration: 5000 });
          }
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors de l\'inscription. Veuillez réessayer plus tard.';
          this.snackBar.open(this.errorMessage, 'Fermer', { duration: 5000 });
        }
      });
    }
  }

  // Méthode pour revenir à la page précédente
  goBack(): void {
    this.location.back();  // Utilise Location pour revenir à la page précédente
  }
}
