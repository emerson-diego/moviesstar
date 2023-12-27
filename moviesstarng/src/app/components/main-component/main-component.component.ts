import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { of } from 'rxjs';
import { catchError } from 'rxjs/internal/operators/catchError';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../types';

@Component({
  selector: 'app-main-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './main-component.component.html',
  styleUrl: './main-component.component.scss',
  providers: [MovieService],
})
export class MainComponent {
  @Input() movieId!: number;
  movie!: MovieDTO;

  constructor(
    private movieService: MovieService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
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
            // Handle the case where movie is not found or an error occurred
            console.log('Movie not found or an error occurred');
          }
        });
    }
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
