<h1 align="center">Proyecto Repartos</h1>
<p align="center"><a href="#project-description">Project Description</a> - <a href="#key-features">Key Features</a> - <a href="#technology-stack">Tech Stack</a></p>

## Project Description

Proyecto Repartos es un sistema de planificación y gestión de repartos diseñado para optimizar la logística de entregas en entornos urbanos. El sistema permite calcular rutas óptimas, gestionar el seguimiento en vivo de repartidores y notificar a clientes sobre el estado de sus pedidos. La solución está diseñada con una arquitectura de microservicios que facilita la escalabilidad, el mantenimiento y la integración de nuevas funcionalidades.

El objetivo principal de este proyecto es:

Optimizar la logística de reparto: Reducir tiempos y costos mediante el cálculo de rutas eficientes y el seguimiento en tiempo real de los repartidores. Mejorar la experiencia del usuario: Permitir a los clientes conocer la ubicación actual del repartidor, el tiempo estimado de llegada y recibir notificaciones sobre el estado de su pedido. Escalabilidad y modularidad: Implementar una arquitectura de microservicios que se pueda escalar y extender conforme crecen las necesidades del negocio.

## Key Features

Desarrollo de Auth-Service y Delivery-Service: Implementar microservicios adicionales para la autenticación (delegada a Keycloak) y para el seguimiento y asignación de pedidos. Integración de Notificaciones: Desarrollar Notification-Service para gestionar notificaciones por email y en la aplicación. Frontend Angular: Crear el proyecto delivery-frontend usando Angular y ngx-leaflet para visualizar mapas interactivos y seguimiento en tiempo real. Optimización del Cálculo de Rutas: Evaluar y elegir entre OpenRouteService, GraphHopper u otras alternativas para el cálculo de rutas óptimas.

## Tech Stack

Backend: Java 23 & Spring Boot 3.4.2
Frontend: Angular integrado con Leaftlet 
Base de datos: MongoDB 
Tecnologías utilizadas: Redis, Docker, Kafka, Keycloack, OpenStreetMap, OpenRouteService, GraphHopper 
Testing: JMeter, Postman, Swagger, Springdoc
