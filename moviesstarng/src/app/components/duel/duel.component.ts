import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MovieService } from '../../services/movie.service';
import { MainComponent } from '../main-component/main-component.component';
import { MovieDTO } from '../types';
import { DuelResultService } from './../../services/duel.service';

@Component({
  selector: 'app-duel',
  standalone: true,
  imports: [CommonModule, MatSnackBarModule, MainComponent],
  templateUrl: './duel.component.html',
  styleUrl: './duel.component.scss',
  providers: [MovieService, DuelResultService],
})
export class MovieDuelComponent implements OnInit {
  movie1?: MovieDTO;
  movie2?: MovieDTO;

  highlightedMovie1: boolean = false;
  highlightedMovie2: boolean = false;

  constructor(
    private movieService: MovieService,
    private snackBar: MatSnackBar,
    private duelResultService: DuelResultService
  ) {}

  ngOnInit() {
    this.loadRandomMovies();
  }

  loadRandomMovies() {
    this.movieService
      .getRandomMovie()
      .subscribe((movie) => (this.movie1 = movie));
    this.movieService
      .getRandomMovie()
      .subscribe((movie) => (this.movie2 = movie));

    console.log(this.movie1);
    console.log(this.movie2);
  }

  onDuelResult(winner: MovieDTO, loser: MovieDTO) {
    if (winner.id !== undefined && loser.id !== undefined) {
      this.movieService.submitDuelResult(winner.id, loser.id).subscribe(() => {
        this.snackBar.open('Duelo atualizado com sucesso', 'Fechar', {
          duration: 3000,
        });
        this.loadRandomMovies(); // Carregar novos filmes para o próximo duelo
        // Após a conclusão do duelo, avise o app-side-component para recarregar
        this.duelResultService.announceDuelResult();
      });
    } else {
      // Lida com o caso em que winner.id ou loser.id é undefined
      console.error('O ID do vencedor ou do perdedor é undefined.');
    }
  }
}
