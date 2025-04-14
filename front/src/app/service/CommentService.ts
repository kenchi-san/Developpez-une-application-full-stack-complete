import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {Commentaire} from "../interfaces/commentaire";

@Injectable({
  providedIn: 'root'
})
export class CommentaireService {
  private apiUrl = `${environment.apiUrl}/commentaire`;

  constructor(private http: HttpClient) {}

  getAllCommentaires(articleId:number): Observable<Commentaire[]> {
    return this.http.get<Commentaire[]>(`${this.apiUrl}/listCommentaire/${articleId}`, { withCredentials: true });
  }

  getCommentaireById(id: number): Observable<Commentaire> {
    return this.http.get<Commentaire>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

  createCommentaire(commentaire: Commentaire): Observable<Commentaire> {
    return this.http.post<Commentaire>(this.apiUrl, commentaire, { withCredentials: true });
  }

  updateCommentaire(id: number, commentaire: Commentaire): Observable<Commentaire> {
    return this.http.put<Commentaire>(`${this.apiUrl}/${id}`, commentaire, { withCredentials: true });
  }

  deleteCommentaire(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }
}
