import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {Article} from "../interfaces";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = `${environment.apiUrl}/article`;

  constructor(private http: HttpClient) {}

  // 🔹 GET : Récupérer tous les articles
  getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl+'/', { withCredentials: true });
  }

  // 🔹 GET : Récupérer un article par ID
  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

  // 🔹 POST : Créer un nouvel article
  createArticle(article: Article): Observable<Article> {
    return this.http.post<Article>(this.apiUrl, article, { withCredentials: true });
  }

  // 🔹 PUT : Modifier un article existant
  updateArticle(id: number, article: Article): Observable<Article> {
    return this.http.put<Article>(`${this.apiUrl}/${id}`, article, { withCredentials: true });
  }

  // 🔹 DELETE : Supprimer un article
  deleteArticle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }
}
