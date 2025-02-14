import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
@NgModule({
imports: [
BrowserAnimationsModule,
MatToolbarModule,
MatCardModule,
],
bootstrap: [] // Si usas bootstrapApplication en main.ts, este módulo podría no necesitar bootstrap
})
export class AppModule { }
