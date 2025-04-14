import { Component, Input, OnInit } from '@angular/core';
import { Commentaire } from "../../interfaces/commentaire";
import { CommentaireService } from "../../service/CommentService";

@Component({
  selector: 'app-commentaire',
  templateUrl: './commentaire.component.html',
  styleUrls: ['./commentaire.component.scss']
})
export class CommentaireComponent implements OnInit {
  @Input() articleId!: number;
  @Input() commentaires!: Commentaire[];
  // newComment: string = '';

  constructor(private commentaireService: CommentaireService) { }

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

  // submitComment(): void {
  //   if (this.newComment.trim()) {
  //     const commentaire: Partial<Commentaire> = {
  //       comment: this.newComment,
  //       article: { id: this.articleId },
  //       // ajouter d'autres champs nécessaires comme l'auteur si requis
  //     };
  //
  //     this.commentaireService.ajouterCommentaire(commentaire).subscribe({
  //       next: (newCommentaire) => {
  //         // Ajouter le nouveau commentaire dans la liste
  //         this.commentaires.push(newCommentaire);
  //         this.newComment = '';  // Réinitialiser l'entrée
  //       },
  //       error: (err) => console.error('Erreur lors de l\'ajout du commentaire', err)
  //     });
  //   }
  // }
}
