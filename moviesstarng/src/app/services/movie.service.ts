import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private baseUrl = 'http://localhost:8083/movies'; // Replace with your actual backend URL

  constructor(private http: HttpClient) {}

  getMovieById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
}
