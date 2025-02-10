# Usa una imagen base con OpenJDK 17
FROM openjdk:23-jdk-slim

# Crea un directorio para la aplicación
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que se ejecuta la aplicación (8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
