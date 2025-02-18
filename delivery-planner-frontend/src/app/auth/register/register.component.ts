import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { AuthService } from '../../core/services/auth.services';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'; // Importa SweetAlert2


@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule
  ]
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellido: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      userType: ['client', Validators.required]  // Puede ser 'client' o 'delivery'
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formData = this.registerForm.value;
      console.log('Formulario válido:', this.registerForm.value);

      // Simula una llamada a la API (puedes reemplazar esto con tu servicio real)
      setTimeout(() => {
        // Muestra una notificación de éxito con SweetAlert2
        Swal.fire({
          icon: 'success',
          title: 'Registro exitoso',
          text: '¡Te has registrado correctamente!',
          confirmButtonText: 'Ir al login'
        }).then((result) => {
          if (result.isConfirmed) {
            // Redirige al usuario al login
            this.router.navigate(['/login']);
          }
        });
      }, 1000); // Simulación de retardo de 1 segundo

      // Transformamos el valor de userType al rol de Keycloak correspondiente
      const payload = {
        nombre: formData.nombre,
        apellido: formData.apellido,
        email: formData.email,
        password: formData.password,
        // Mapeo: 'client' -> 'cliente_client_role', 'delivery' -> 'repartidor_client_role'
        rol: formData.userType === 'client' ? 'cliente_client_role' : 'repartidor_client_role'
      };

      this.authService.register(payload).subscribe({
        next: (response) => {
          console.log('Registro exitoso:', response);
          this.router.navigate(['/login']); // Redirige al login tras el registro
        },
        error: (error) => {
          console.error('Error al registrarse:', error);
        }
      });
    }
  }
}
