import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ArticleService} from "../../service/ArticleService";
import {Article} from "../../interfaces";
import {Location} from "@angular/common";
import {CommentaireService} from "../../service/CommentService";
import {Commentaire} from "../../interfaces/commentaire";

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article!: Article;
  private location = inject(Location);  // Injection du service Location
commentaires!: Commentaire[];
  constructor(private route: ActivatedRoute,
              private articleService: ArticleService,
              private commentaireService: CommentaireService,
              ) {
  }

//
  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('articleId'); // <-- nom correct dans la route
    const id = idParam ? Number(idParam) : null;

    if (id !== null && !isNaN(id)) {
      this.articleService.getArticleById(id).subscribe({
        next: (data) => {
          this.article = data;
          this.loadCommentaires(id);

        },
        error: (error) => {
          console.error('Erreur lors de la récupération de l\'article', error);
        }
      });
    } else {
     return  console.error("Il n'y a pas d'article trouvé")
    }
  }

  // submitComment() {
  //   if (this.newComment.trim()) {
  //     // Appelle ton service pour enregistrer le commentaire
  //     console.log('Nouveau commentaire:', this.newComment);
  //    this.newComment = '';
  //   }
  // }
  goBack(): void {
    this.location.back();
  }
  loadCommentaires(articleId: number): void {
    this.commentaireService.getAllCommentaires(articleId).subscribe({
      next: (data) => {
        this.commentaires = data;
      },
      error: (err) => console.error('Erreur lors du chargement des commentaires', err)
    });
  }
}
