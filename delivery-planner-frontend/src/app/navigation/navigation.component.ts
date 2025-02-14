import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, MatToolbarModule, MatButtonModule, RouterLink],
  template: `
    <mat-toolbar color="primary">
      <span>Delivery Planner</span>
      <nav class="menu-items">
        <a mat-button routerLink="/login">Login</a>
        <a mat-button routerLink="/register">Registro</a>
        <a mat-button routerLink="/map">Mapa</a>
      </nav>
    </mat-toolbar>
  `,
  styles: [`
    .menu-items {
      margin-left: auto;
    }
  `]
})
export class NavigationComponent {}
