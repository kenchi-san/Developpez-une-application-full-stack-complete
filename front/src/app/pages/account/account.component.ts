import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../service/AccountService';
import { Router } from '@angular/router';
import { Me } from '../../interfaces/me';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';

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
    private fb: FormBuilder
  ) {}

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
      const formValues = this.profileForm.value;

      const newFullName = formValues.fullname?.trim();
      const newEmail = formValues.email?.trim();
      const newPassword = formValues.password?.trim();

      const emailChanged = currentEmail !== newEmail;

      const updatePayload = {
        fullName: newFullName,
        email: newEmail,
        password: newPassword
      };

      const proceedWithUpdate = () => {
        this.accountService.updateAccount(updatePayload).subscribe({
          next: (updatedUser) => {
            if (emailChanged) {
              this.cookieService.delete('JWT_Token');
              this.router.navigate(['/login']);
            } else {
              console.log('✅ Profil mis à jour sans déconnexion :', updatedUser);
            }
          },
          error: (error) => {
            console.error('❌ Erreur lors de la mise à jour du profil', error);
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
