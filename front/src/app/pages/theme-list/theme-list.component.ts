import {Component, OnInit} from '@angular/core';
import {Theme} from "../../interfaces";
import {ArticleService} from "../../service/ArticleService";
import {Router} from "@angular/router";
import {ThemeListService} from "../../service/ThemeListService";

@Component({
  selector: 'app-theme-list',
  templateUrl: './theme-list.component.html',
  styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit {
  themes: Theme[] = [];

  constructor(
    private themeListService: ThemeListService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes() {
    this.themeListService.getListThemes().subscribe({
      next: (data: Theme[]) => this.themes = data,
      error: (err: String) => console.error('Erreur lors de l\'affichage des thèmes', err)
    })
  }
  goToDetail(id: number): void {
    this.router.navigate(['/theme/detail/', id]);
  }
  subscribeToTheme(themeId: number): void {
    this.themeListService.followTeme(themeId).subscribe({
      next: () => console.log('✅ Abonné au thème', themeId),
      error: (err) => console.error('❌ Erreur abonnement :', err)
    });
  }

}
