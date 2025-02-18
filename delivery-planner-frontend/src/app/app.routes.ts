import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { MapComponent } from './map/map.component';
import { ProfileComponent } from './profile/profile.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
    { path: 'profile', component: ProfileComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'map', component: MapComponent },
  { path: '', redirectTo: '/map', pathMatch: 'full' },
  { path: '**', redirectTo: '/map' }
];
