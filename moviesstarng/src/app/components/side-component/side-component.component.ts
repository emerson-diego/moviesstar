import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../types';

@Component({
  selector: 'app-side-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './side-component.component.html',
  styleUrl: './side-component.component.scss',
  providers: [MovieService],
})
export class SideComponent implements OnInit {
  topMovies: MovieDTO[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.movieService
      .getTop25Movies()
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]); // Retorna um array vazio em caso de erro
        })
      )
      .subscribe((movies) => {
        this.topMovies = movies;
      });
  }
}
