import {Component, Input, OnInit} from '@angular/core';
import {Theme} from "../../interfaces";
import {Router} from "@angular/router";
import {ThemeListService} from "../../service/ThemeListService";

@Component({
  selector: 'app-theme-list',
  templateUrl: './theme-list.component.html',
  styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit {
  @Input() onlySubscribed: boolean = false;

  themes: Theme[] = [];

  constructor(
    private themeListService: ThemeListService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes() {
    this.themeListService.getListThemes().subscribe({
      next: (data: Theme[]) => {
        this.themes = this.onlySubscribed
          ? data.filter(theme => theme.subscribed)
          : data;
      },
      error: (err: string) => console.error('Erreur lors de l\'affichage des thèmes', err)
    });
  }

  goToDetail(id: number): void {
    this.router.navigate(['/theme/detail/', id]);
  }

  subscribeToTheme(theme: Theme): void {
    this.themeListService.manageSubscribe(theme.id).subscribe({
      next: () => {
        theme.subscribed = !theme.subscribed;
        console.log(`${theme.subscribed ? 'Abonné' : 'Désabonné'} au thème`, theme.id);

        if (this.onlySubscribed && !theme.subscribed) {
          this.themes = this.themes.filter(t => t.id !== theme.id);
        }
      },
      error: (err) => {
        console.error('Erreur abonnement :', err);
      }
    });
  }
}
