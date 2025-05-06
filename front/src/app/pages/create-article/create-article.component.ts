import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {ThemeListService} from "../../service/ThemeListService";
import {ListTheme} from '../../interfaces/listTheme';  // Interface pour Theme
import {ArticleService} from "../../service/ArticleService";
import {CreateArticle} from "../../interfaces/createArticle";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  themes: ListTheme[] = [];
  createArticle: CreateArticle = {
    title: '',
    content: '',
    theme: {id: 0, name: ''}
  };

  constructor(
    private router: Router,
    private location: Location,
    private articleService: ArticleService,
    private themeService: ThemeListService,
    private snackBar: MatSnackBar
  ) {
  }

  ngOnInit(): void {
    this.loadThemes();
  }

  // Méthode pour charger les thèmes
  loadThemes(): void {
    this.themeService.getAllThemes().subscribe({
      next: (data: ListTheme[]) => {
        this.themes = data;
      },
      error: (err: any) => {
        console.error('Erreur lors de la récupération des thèmes', err);
      }
    });
  }

  onSubmit(): void {
    this.articleService.createArticle(this.createArticle).subscribe({
      next: () => {
        this.snackBar.open('✅ Article créé avec succès !', 'Fermer', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        this.router.navigate(['/']);
      },
      error: (err: any) => {
        console.error('Erreur lors de la création de l\'article', err);
        this.snackBar.open('❌ Erreur lors de la création de l\'article.', 'Fermer', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  // Méthode pour revenir en arrière
  goBack(): void {
    this.location.back();
  }
}
