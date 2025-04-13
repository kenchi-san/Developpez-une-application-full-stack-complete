import {Author} from "./author";
import {Theme} from "./theme";

export interface Article {
  id: number;
  title: string;
  content: string;
  author: Author;
  created: Date;
  updated: Date;
  theme: Theme;
}
