import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../service/AccountService';
import {Router} from '@angular/router';
import {Me} from '../../interfaces/me';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CookieService} from 'ngx-cookie-service';
import {AuthService} from "../../service/AuthService";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {
  public dataUser: Me = {} as Me;
  public profileForm!: FormGroup;

  constructor(
    private accountService: AccountService,
    private router: Router,
    private cookieService: CookieService,
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar

  ) {
  }

  ngOnInit(): void {
    this.loadAccount();
  }

  loadAccount(): void {
    this.accountService.getAccount().subscribe({
      next: (data: Me) => {
        this.dataUser = data;

        this.profileForm = this.fb.group({
          fullname: [data.fullName ?? '', Validators.required],
          email: [data.email ?? '', [Validators.required, Validators.email]],
          password: ['']
        });
      },
      error: (err: string) => {
        console.error('❌ Erreur lors de la récupération des informations utilisateur', err);
      }
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      const currentEmail = this.dataUser.email?.trim();
      const currentFullName = this.dataUser.fullName?.trim();
      const currentPassword = this.dataUser.password?.trim();

      const formValues = this.profileForm.value;

      const newFullName = formValues.fullname?.trim();
      const newEmail = formValues.email?.trim();
      const newPassword = formValues.password?.trim();

      const emailChanged = currentEmail !== newEmail;
      const fullNameChanged = currentFullName !== newFullName;
      const passwordChanged = currentPassword !== newPassword;

      // Ne rien faire si rien n’a changé
      if (!emailChanged && !fullNameChanged && !passwordChanged) {
        console.log("Aucune modification détectée.");
        return;
      }

      const updatePayload = {
        fullName: newFullName,
        email: newEmail,
        password: newPassword
      };

      const proceedWithUpdate = () => {
        this.accountService.updateAccount(updatePayload).subscribe({
          next: (updatedUser) => {
            let changedFields: string[] = [];

            if (emailChanged) changedFields.push('email');
            if (fullNameChanged) changedFields.push('nom complet');
            if (passwordChanged) changedFields.push('mot de passe');

            const message = `✅ ${changedFields.join(', ')} modifié${changedFields.length > 1 ? 's' : ''} avec succès.`;

            this.snackBar.open(message, 'Fermer', {
              duration: 4000,
              panelClass: ['snackbar-success']
            });

            if (emailChanged) {
              this.authService.logout();
              this.router.navigate(['/login']);
            } else {
              this.router.navigate(['/listArticle']);
            }
          },
          error: (error) => {
            console.error('❌ Erreur lors de la mise à jour du profil', error);
            this.snackBar.open('❌ Erreur lors de la mise à jour.', 'Fermer', {
              duration: 4000,
              panelClass: ['snackbar-error']
            });
          }
        });
      };



      if (emailChanged) {
        const confirmed = window.confirm("⚠️ Vous avez modifié votre adresse email. Cela vous déconnectera. Continuer ?");
        if (confirmed) {
          proceedWithUpdate();
        }
      } else {
        proceedWithUpdate();
      }
    }
  }

}
