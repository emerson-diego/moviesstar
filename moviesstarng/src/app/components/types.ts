export class MovieDTO {
  id?: number;
  title?: string;
  posterPath?: string;
  overview?: string;
  director?: string;
  mainActors?: string;
  genreDescription?: string;
  posterUrl?: string;
  nationality?: string;
  trailerUrl?: string;
  imdbRating?: string;
  releaseDate?: string;
  braziliamTitle?: string;
  score?: number;

  constructor(init?: Partial<MovieDTO>) {
    Object.assign(this, init);
  }
}
