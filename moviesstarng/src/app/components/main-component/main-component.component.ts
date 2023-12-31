import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
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
  @Input() movie?: MovieDTO; // Recebe o filme como entrada
  @Output() filmeAtualizado = new EventEmitter<void>();

  constructor(
    private movieService: MovieService,
    private snackBar: MatSnackBar,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    /* if (!this.movie) {
      this.loadRandomMovie();
    }*/
  }

  loadRandomMovie() {
    /*this.movieService.getRandomMovie().subscribe((movie) => {
      this.movie = movie;
    });*/
  }

  toggleWatchedStatus(event: Event, movie: MovieDTO): void {
    event.stopPropagation();
    if (!movie || movie.id === undefined) {
      console.error('O filme não está definido ou não possui um ID válido.');
      return;
    }

    const newWatchedStatus = !movie.watched;

    this.movieService.markMovieAsWatched(movie.id, newWatchedStatus).subscribe(
      (updatedMovie) => {
        if (updatedMovie) {
          if (this.movie) {
            this.movie.watched = updatedMovie.watched;
          }
          const message = updatedMovie.watched
            ? 'Filme marcado como assistido.'
            : 'Filme marcado como não assistido.';
          this.snackBar.open(message, 'Fechar', { duration: 2000 });
          // Emita o evento após a atualização do status do filme
          this.filmeAtualizado.emit();
        } else {
          console.error('Falha ao marcar o filme.');
        }
      },
      (error) => {
        console.error('Erro ao marcar o filme', error);
      }
    );
  }

  getSafeYoutubeUrl(url?: string): SafeResourceUrl {
    if (!url) {
      // Retorne uma URL segura padrão ou vazia se não houver URL
      return this.sanitizer.bypassSecurityTrustResourceUrl('');
    }

    let videoId = url.split('v=')[1];
    let embedUrl = `https://www.youtube.com/embed/${videoId}`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }
}
