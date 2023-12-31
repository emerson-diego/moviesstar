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
    this.getRandomMovies();
  }

  getRandomMovies() {
    this.movieService.getRandomMovies().subscribe((movies) => {
      /*  if (
        this.haveBothMoviesNotBeenWatched(movies[0], movies[1]) ||
        this.areMoviesEqual(movies[0], movies[1])
      ) {
        // Chama a função novamente para obter novos filmes
        this.getRandomMovies();
      } else {*/
      // Atribui os filmes obtidos aos membros da classe
      this.movie1 = movies[0];
      this.movie2 = movies[1];

      // console.log(this.movie1);
      // console.log(this.movie2);
      // }
    });
  }

  haveBothMoviesNotBeenWatched(movie1: MovieDTO, movie2: MovieDTO) {
    // Suponhamos que você tenha uma propriedade em sua classe para rastrear se os filmes foram assistidos.
    // Por exemplo, você pode ter duas propriedades booleanas movie1Watched e movie2Watched.
    // Aqui está uma implementação de exemplo com base nessa suposição:
    return !movie1?.watched || !movie2?.watched;
  }

  areMoviesEqual(movie1: MovieDTO, movie2: MovieDTO) {
    // Suponhamos que os filmes tenham um identificador único, como um campo "id".
    // Aqui está uma implementação de exemplo com base nessa suposição:
    return movie1.id === movie2.id;
  }

  onDuelResult(winner: MovieDTO, loser: MovieDTO) {
    if (winner.id !== undefined && loser.id !== undefined) {
      this.movieService.submitDuelResult(winner.id, loser.id).subscribe(() => {
        this.snackBar.open('Duelo atualizado com sucesso', 'Fechar', {
          duration: 3000,
        });
        // this.duelResultService.announceDuelResult();
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
