import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ListTheme } from "../interfaces/listTheme";
import {Article, Theme} from "../interfaces";

@Injectable({
  providedIn: 'root'
})
export class ThemeListService {
  private apiUrl = `${environment.apiUrl}/themes`;

  constructor(private http: HttpClient) {}

  getAllThemes(): Observable<ListTheme[]> {
    return this.http.get<ListTheme[]>(`${this.apiUrl}/list`, { withCredentials: true });
  }
  getListThemes(): Observable<Theme[]>{
    return this.http.get<Theme[]>(`${this.apiUrl}/theme-list`, { withCredentials: true });
  }
  followTeme(id: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/follow/${id}`, null, {
      withCredentials: true,
      responseType: 'text' as const  // très important ici
    });
  }
}
