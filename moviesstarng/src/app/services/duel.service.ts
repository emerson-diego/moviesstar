import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DuelResultService {
  private duelResultSubject = new Subject<void>();

  duelResult$ = this.duelResultSubject.asObservable();

  announceDuelResult() {
    console.log('mudou');
    this.duelResultSubject.next();
  }
}
