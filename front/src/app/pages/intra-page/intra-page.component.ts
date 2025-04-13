import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/ArticleService";

@Component({
  selector: 'app-intra-page',
  templateUrl: './intra-page.component.html',
  styleUrls: ['./intra-page.component.scss']
})
export class IntraPageComponent implements OnInit {

  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.articleService.getAllArticles().subscribe(articles => {
      console.log('Liste des articles:', articles);
    })
  }

}
