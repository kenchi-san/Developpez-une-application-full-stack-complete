import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import {Me} from "../interfaces/me";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  getAccount(): Observable<Me> {
    return this.http.get<Me>(this.apiUrl+'/me', { withCredentials: true });
  }
  /**
   * Met à jour les infos de l'utilisateur
   * @param updatedData Données modifiées (fullname, email, password)
   */
  updateAccount(updatedData: Partial<Me>): Observable<Me> {
    return this.http.put<Me>(`${this.apiUrl}/update`, updatedData);
  }}
