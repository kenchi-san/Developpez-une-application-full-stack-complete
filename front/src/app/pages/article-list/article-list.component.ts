import { Component, OnInit } from '@angular/core';
import {Article} from "../../interfaces";
import {ArticleService} from "../../service/ArticleService";

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.loadArticles();
  }
  loadArticles(): void {
    this.articleService.getAllArticles().subscribe({
      next: (data) => {
        this.articles = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des articles', err);
      }
    });
  }
}
