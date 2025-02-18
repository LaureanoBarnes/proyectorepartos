import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/services/auth.services';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  userProfile: any = {};

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getUserProfile().subscribe({
      next: (data) => {
        this.userProfile = data;
      },
      error: (error) => {
        console.error('Error al cargar el perfil:', error);
      },
    });
  }

  editProfile(): void {
    console.log('Editar perfil');
    // Lógica para editar el perfil
  }

  changePassword(): void {
    console.log('Cambiar contraseña');
    // Lógica para cambiar la contraseña
  }
}
