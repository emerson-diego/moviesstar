import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieDTO } from '../components/types';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private baseUrl = 'http://localhost:8083/movies'; // Replace with your actual backend URL

  constructor(private http: HttpClient) {}

  getMovieById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  getTop25Movies(): Observable<MovieDTO[]> {
    return this.http.get<MovieDTO[]>(`${this.baseUrl}/top25`);
  }

  markMovieAsWatched(movieId: number, watched: boolean): Observable<MovieDTO> {
    // Construindo os parâmetros de consulta
    const queryParams = new HttpParams().set('watched', watched.toString());

    // Enviando a requisição usando `request` com os parâmetros de consulta
    return this.http.request<MovieDTO>(
      'put',
      `${this.baseUrl}/${movieId}/watched`,
      {
        params: queryParams,
      }
    );
  }

  getRandomMovies(): Observable<MovieDTO[]> {
    return this.http.get<MovieDTO[]>(`${this.baseUrl}/random`);
  }

  submitDuelResult(winnerId: number, loserId: number): Observable<any> {
    // Crie um objeto HttpParams para incluir os parâmetros na URL
    const params = new HttpParams()
      .set('winnerId', winnerId.toString())
      .set('loserId', loserId.toString());

    // Faça a solicitação HTTP usando os parâmetros de consulta
    return this.http.post(`${this.baseUrl}/duel`, null, { params });
  }
}
