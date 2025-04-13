import { Component, HostListener, inject, OnInit } from '@angular/core';
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
export class RegisterComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);
  private location = inject(Location);  // Injection du service Location

  isLoading = false;
  errorMessage: string | null = null;

  // Définition du formulaire réactif pour l'inscription
  registerForm: FormGroup = this.fb.group({
    fullName: ['', [Validators.required]],  // Nom complet
    email: ['', [Validators.required, Validators.email]],  // Email valide
    password: ['', [Validators.required, Validators.minLength(6)]],  // Mot de passe valide
  });

  // Variable pour gérer l'affichage du logo et de la navbar en mode desktop
  isDesktop: boolean = true;

  ngOnInit(): void {
    this.checkScreenWidth();  // Vérifie la largeur de l'écran au chargement du composant
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenWidth();  // Vérifie la largeur de l'écran à chaque redimensionnement
  }

  // Méthode pour vérifier la largeur de l'écran et ajuster isDesktop
  checkScreenWidth() {
    if (window.innerWidth >= 600) {
      this.isDesktop = true;  // Affiche la navbar si l'écran est assez large (desktop)
    } else {
      this.isDesktop = false;  // Cache la navbar si l'écran est trop petit (mobile)
    }
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      this.errorMessage = null; // Réinitialisation de l'erreur

      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response) {
            this.router.navigate(['/login']);
          }
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = err?.error?.detail || 'Erreur lors de l\'inscription. Veuillez réessayer plus tard.';
          this.snackBar.open(this.errorMessage || '', 'Fermer', { duration: 5000 });
        }
      });
    }
  }

  // Méthode pour revenir à la page précédente
  goBack(): void {
    this.location.back();  // Utilise Location pour revenir à la page précédente
  }
}
