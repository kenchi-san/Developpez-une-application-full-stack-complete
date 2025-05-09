import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Article } from '../../interfaces';
import { ArticleService } from '../../service/ArticleService';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  sortAscending = true;

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    this.articleService.getAllArticles().subscribe({
      next: (data: Article[]) => this.articles = data,
      error: (err: String) => console.error('Erreur lors de la récupération des articles', err)
    });
  }

  toggleSortOrder(): void {
    this.sortAscending = !this.sortAscending;
    this.articles.sort((firstArticleArray, secondeArticleArray) => {
      const actualSort = new Date(firstArticleArray.created).getTime();
      const newSort = new Date(secondeArticleArray.created).getTime();
      return this.sortAscending ? actualSort - newSort : newSort - actualSort;
    });
  }

  goToDetail(id: number): void {
    this.router.navigate(['/article/detail/', id]);
  }
}
