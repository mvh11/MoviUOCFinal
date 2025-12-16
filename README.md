

## Integrantes

- Maximiliano Venegas  
- Elías Lobos  



## Funcionalidades

### Aplicación móvil
- Pantalla de inicio de sesión (LoginActivity).
- Navegación entre pantallas:
  - Login → Pantalla principal (MainActivity).
- Validación básica de acceso.
- Persistencia simple de sesión.
- Consumo de servicios REST desde la aplicación Android.

### Backend (Microservicios)
- Gestión de usuarios.
- Gestión de conductores.
- Gestión de viajes.
- Arquitectura basada en microservicios con Spring Boot.
- Comunicación mediante API REST.



## Endpoints utilizados

### Microservicio Usuarios (usuarios-service)

Base URL:
http://localhost:8080

yaml
Copiar código

Endpoints:
- GET /usuarios
- GET /usuarios/{id}
- POST /usuarios
- PUT /usuarios/{id}
- DELETE /usuarios/{id}
- POST /usuarios/login



### Microservicio Conductores (conductores-service)

Base URL:
http://localhost:8081

yaml
Copiar código

Endpoints:
- GET /conductores
- GET /conductores/{id}
- POST /conductores
- PUT /conductores/{id}
- DELETE /conductores/{id}



### Microservicio Viajes (viajes-service)

Base URL:
http://localhost:8082

yaml
Copiar código

Endpoints:
- GET /viajes
- GET /viajes/{id}
- POST /viajes


## Instrucciones para ejecutar el proyecto

### Backend (Microservicios)

1. Abrir cada microservicio en IntelliJ IDEA:
   - usuarios-service
   - conductores-service
   - viajes-service
2. Ejecutar cada uno como Spring Boot Application.
3. Verificar que los puertos estén activos:
   - 8080
   - 8081
   - 8082



### Aplicación Android

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar la aplicación en un emulador o dispositivo físico.
4. Iniciar sesión desde la pantalla de login para acceder a la pantalla principal.



## APK firmado

El APK firmado se genera desde Android Studio:

Build → Generate Signed Bundle / APK

Ubicación del APK:
app/build/outputs/apk/release/app-release.apk

yaml
Copiar código



## Keystore (.jks)

- El proyecto utiliza un archivo .jks para la firma del APK.
- Ubicación del archivo (ejemplo):
app/keystore/moviuoc-release.jks

yaml
Copiar código

El archivo .jks no se incluye en el repositorio por motivos de seguridad.



## Código fuente

El repositorio contiene:
- Código fuente de la aplicación móvil Android.
- Código fuente de los microservicios:
  - usuarios-service
  - conductores-service
  - viajes-service
