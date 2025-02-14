import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  template: `
    <div #mapContainer id="map" style="height: 600px; width: 100%;"></div>
  `,
  styles: []
})
export class MapComponent implements AfterViewInit {
  @ViewChild('mapContainer', { static: true }) mapContainer!: ElementRef;
  private map: any;

  ngAfterViewInit(): void {
    // Verifica que el contenedor del mapa tenga dimensiones
    if (this.mapContainer.nativeElement.offsetHeight === 0 || this.mapContainer.nativeElement.offsetWidth === 0) {
      console.error('El contenedor del mapa no tiene dimensiones. Asegúrate de que el contenedor esté visible.');
      return;
    }

    // Inicializa el mapa
    this.initializeMap();

    // Forza la actualización del mapa después de un breve retraso
    setTimeout(() => {
      this.invalidateMapSize();
    }, 100);
  }

  private initializeMap(): void {
    this.map = L.map(this.mapContainer.nativeElement).setView([40.4168, -3.7038], 13); // Coordenadas de Madrid

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    // Agrega un marcador de ejemplo
    L.marker([40.4168, -3.7038])
      .addTo(this.map)
      .bindPopup('Madrid')
      .openPopup();
  }

  private invalidateMapSize(): void {
    if (this.map) {
      this.map.invalidateSize(); // Forza la actualización del mapa
    }
  }
}
