import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatError, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';

@NgModule({
imports: [
BrowserAnimationsModule,
MatToolbarModule,
MatCardModule,
CommonModule,
MatButtonModule,
ReactiveFormsModule,
MatError,
FormsModule,
MatFormFieldModule,
MatInputModule,
MatRadioModule,
RouterModule.forRoot(routes)

],
bootstrap: [] // Si usas bootstrapApplication en main.ts, este módulo podría no necesitar bootstrap
})
export class AppModule { }
