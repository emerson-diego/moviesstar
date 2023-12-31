import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EMPTY, Subject, catchError, of, takeUntil } from 'rxjs';
import { DuelResultService } from '../../services/duel.service';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../types';

@Component({
  selector: 'app-side-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './side-component.component.html',
  styleUrl: './side-component.component.scss',
  providers: [],
})
export class SideComponent implements OnInit {
  private destroy$ = new Subject<void>();
  topMovies: MovieDTO[] = [];

  constructor(
    private movieService: MovieService,
    private duelResultService: DuelResultService
  ) {}

  ngOnInit() {
    this.loadTopMovies();
    this.duelResultService.duelResult$
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.error('Erro ao carregar os filmes: ', error);
          return EMPTY;
        })
      )
      .subscribe(() => {
        console.log('recarregou');
        this.loadTopMovies();
      });
  }

  loadTopMovies() {
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

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
