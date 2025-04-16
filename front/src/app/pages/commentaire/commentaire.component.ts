import {Component, Input, OnInit} from '@angular/core';
import {Commentaire} from "../../interfaces/commentaire";
import {CommentaireService} from "../../service/CommentService";
import {AuthService} from "../../service/AuthService";

@Component({
  selector: 'app-commentaire',
  templateUrl: './commentaire.component.html',
  styleUrls: ['./commentaire.component.scss']
})
export class CommentaireComponent implements OnInit {
  @Input() articleId!: number;
  @Input() commentaires: Commentaire[] = [];
  newComment: string = '';

  constructor(
    private commentaireService: CommentaireService,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    if (this.articleId) {
      this.loadCommentaires(this.articleId);
    }
  }

  loadCommentaires(articleId: number): void {
    this.commentaireService.getAllCommentaires(articleId).subscribe({
      next: (data) => this.commentaires = data,
      error: (err) => console.error('Erreur lors du chargement des commentaires', err)
    });
  }

  submitComment(): void {
    const trimmedComment = this.newComment.trim();
    if (!trimmedComment) return;

    this.commentaireService.createCommentaire(this.articleId, trimmedComment).subscribe({
      next: (created) => {
        this.commentaires.push(created);
        this.newComment = '';
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout du commentaire :', err);
      }
    });
  }
}
