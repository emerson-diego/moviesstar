import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MovieDuelComponent } from './components/duel/duel.component';
import { HeaderComponent } from './components/header/header.component';
import { MainComponent } from './components/main-component/main-component.component';
import { SideComponent } from './components/side-component/side-component.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HttpClientModule,
    HeaderComponent,
    MainComponent,
    SideComponent,
    MovieDuelComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'moviesstarng';
}
