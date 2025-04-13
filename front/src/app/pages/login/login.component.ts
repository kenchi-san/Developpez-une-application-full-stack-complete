import { Component, HostListener, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from "../../service/AuthService";
import { MatIconModule } from "@angular/material/icon";  // Importer MatSnackBar
import { Location } from '@angular/common';  // Importer Location pour la navigation

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);
  private location = inject(Location);  // Injection du service Location

  isLoading = false;
  errorMessage: string | null = null;

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  // Variable pour gérer l'affichage de la navbar en mode desktop
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
    if (this.loginForm.valid) {
      this.isLoading = true;  // Début du chargement
      this.errorMessage = null;  // Réinitialiser les erreurs

      // Appeler la méthode de login dans le service AuthService
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response) {
            this.router.navigate(['/listArticle']);  // Redirige vers la page protégée après une connexion réussie
          } else {
            this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect.';
            this.snackBar.open(this.errorMessage, 'Fermer', { duration: 5000 });
          }
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors de la connexion. Veuillez réessayer plus tard.';
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
