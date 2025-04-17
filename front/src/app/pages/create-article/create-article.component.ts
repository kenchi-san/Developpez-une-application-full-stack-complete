import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ThemeListService } from "../../service/ThemeListService";
import { ListTheme } from '../../interfaces/listTheme';  // Interface pour Theme
import { ArticleService } from "../../service/ArticleService";
import { CreateArticle } from "../../interfaces/createArticle";

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
    theme: { id: 0, name: '' }
  };

  constructor(
    private router: Router,
    private location: Location,
    private articleService: ArticleService,   // Injection du service pour créer un article
    private themeService: ThemeListService    // Injection du service pour récupérer les thèmes
  ) {}

  ngOnInit(): void {
    this.loadThemes();  // Charger les thèmes au démarrage
  }

  // Méthode pour charger les thèmes
  loadThemes(): void {
    this.themeService.getAllThemes().subscribe({
      next: (data: ListTheme[]) => {
        this.themes = data;  // Remplir la liste des thèmes
      },
      error: (err: any) => {
        console.error('Erreur lors de la récupération des thèmes', err);
      }
    });
  }

   onSubmit(): void {

    // Appel au service pour créer l'article
    this.articleService.createArticle(this.createArticle).subscribe({
      next: () => {
        this.router.navigate(['/']);  // Naviguer vers la page d'accueil ou la liste des articles
      },
      error: (err: any) => {
        console.error('Erreur lors de la création de l\'article', err);
      }
    });
  }

  // Méthode pour revenir en arrière
  goBack(): void {
    this.location.back();
  }
}
