export interface Commentaire {
  id: number;
  comment: string;
  author: {
    id: number;
    fullName: string;
  };
  article: { id: number };
}
