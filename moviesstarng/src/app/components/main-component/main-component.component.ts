import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { of } from 'rxjs';
import { catchError } from 'rxjs/internal/operators/catchError';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../types';

@Component({
  selector: 'app-main-component',
  standalone: true,
  imports: [CommonModule, MatSnackBarModule],
  templateUrl: './main-component.component.html',
  styleUrl: './main-component.component.scss',
  providers: [MovieService],
})
export class MainComponent {
  movie!: MovieDTO;
  movies: MovieDTO[] = [];

  constructor(
    private movieService: MovieService,
    private sanitizer: DomSanitizer,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    //this.loadMovieDetails();
    this.loadRandomMovie();
  }

  loadRandomMovie() {
    this.movieService.getRandomMovie().subscribe((movie) => {
      this.movie = movie;
    });
  }

  /*loadMovieDetails(): void {
    if (this.movieId) {
      this.movieService
        .getMovieById(this.movieId)
        .pipe(
          catchError((error) => {
            console.error('Error fetching movie', error);
            return of(null); // Handle the error and return a null observable
          })
        )
        .subscribe((data) => {
          if (data) {
            this.movie = data;
          } else {
            console.log('Movie not found or an error occurred');
          }
        });
    }
  }*/

  getSafeYoutubeUrl(url?: string): SafeResourceUrl {
    if (!url) {
      // Retorne uma URL segura padrão ou vazia se não houver URL
      return this.sanitizer.bypassSecurityTrustResourceUrl('');
    }

    let videoId = url.split('v=')[1];
    let embedUrl = `https://www.youtube.com/embed/${videoId}`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }

  toggleWatchedStatus(movie: MovieDTO): void {
    if (!movie || movie.id === undefined) {
      console.error('O filme não está definido ou não possui um ID válido.');
      return;
    }

    this.movieService
      .markMovieAsWatched(movie.id, !movie.watched)
      .pipe(
        catchError((error) => {
          console.error('Erro ao marcar o filme', error);
          return of(null);
        })
      )
      .subscribe((updatedMovie) => {
        if (updatedMovie) {
          // Atualiza apenas as propriedades relevantes do filme
          this.movie.watched = updatedMovie.watched;
          const message = updatedMovie.watched
            ? 'Filme marcado como assistido.'
            : 'Filme marcado como não assistido.';
          this.snackBar.open(message, 'Fechar', { duration: 2000 });
        } else {
          console.error('Falha ao marcar o filme.');
        }
      });
  }
}
