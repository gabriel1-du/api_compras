# Proyecto de microservicios : microservicio de compras para una tienda de historietas.

> En este repositorio se guardan los archivos de la api de compras (`api_compras`) desarrollada con Springboot y Java. Esta api gestiona carritos, listas de deseos, medios de pago, boletas e historial de compras, y pertenece a un sistema de microservicios del siguiente repositorio : https://github.com/gabriel1-du/Optativo-Desarollo-Java-Springboot-Cloud

## Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Frameworks / Librerías:** Springboot 4.1.1, Spring Data JPA, Spring RestClient, Lombok
* **Base de datos:** MySQL (`compras_db`)
* **Puerto:** 8081
* **Otras herramientas:** Git, Maven

---

## Diagrama de la base de datos 

![MER](<Base de datos/MERCompras.png>)

El script de creación de las tablas (`MEDIOS_PAGO`, `CARRITOS`, `PRODUCTOS_CARRITO`, `LISTAS_DESEOS`, `PRODUCTOS_LISTA_DESEOS`, `HISTORIAL_COMPRAS` y `BOLETAS`) está en `Base de datos/compras_bda.txt`.

Relaciones principales:

* `HISTORIAL_COMPRAS` 1 ──► N `BOLETAS` (relación física por `id_historial`).
* `MEDIOS_PAGO` 1 ──► N `BOLETAS` (relación física por `id_medio_pago`).
* `CARRITOS` 1 ──► N `PRODUCTOS_CARRITO` y `CARRITOS` 1 ──► N `BOLETAS`.
* `LISTAS_DESEOS` 1 ──► N `PRODUCTOS_LISTA_DESEOS`.
* `id_usuario` e `id_producto` son **referencias lógicas** a otros microservicios (usuarios e historietas), no tienen llave foránea en esta base de datos.

---
## Estructura de carpetas

```text
api_compras/
├── Base de datos/
│   └── compras_bda.txt                 # Script SQL de la base de datos
└── api_compras/
    ├── pom.xml
    └── src/main/
        ├── resources/
        │   └── application.properties  # Puerto, conexión MySQL y configuración JPA
        └── java/com/example/api_compras/
            ├── ApiComprasApplication.java
            ├── Controller/             # Endpoints REST
            ├── Service/                # Interfaces con el contrato de la lógica de negocio
            ├── ServiceImpl/            # Implementación de la lógica de negocio
            ├── Repository/             # Interfaces Spring Data JPA
            ├── Model/                  # Entidades JPA (tablas)
            ├── DTO/                    # DTOs y Mappers por entidad
            │   ├── BoletaDTO/
            │   ├── CarritoDTO/
            │   ├── HistorialComprasDTO/
            │   ├── ListaDeseosDTO/
            │   ├── ProductoCarritoDTO/
            │   ├── ProductoListaDeseosDTO/
            │   └── RestClientDTO/      # DTOs de las respuestas de microservicios externos
            └── RestClient/             # Comunicación con otros microservicios
                ├── RestClientConfig.java
                ├── UsuarioClient.java
                └── HistorietaClient.java
```

---
## Arquitectura y Flujo de Datos

El proyecto implementa una arquitectura en capas basada en separación de responsabilidades:

```text
[ Cliente / Frontend ]
         │ ▲
(HTTP)   ▼ │ (JSON / DTO)
   ┌─────────────┐
   │ Controller  │ ──► Expone los endpoints REST y gestiona la entrada/salida HTTP
   └─────────────┘
         │ ▲
(DTOs)   ▼ │
   ┌─────────────┐
   │   Service   │ ──► Interfaz: define el contrato de la lógica de negocio
   └─────────────┘
         │ ▲
         ▼ │
   ┌─────────────┐        ┌─────────────┐
   │ ServiceImpl │ ◄────► │ RestClient  │ ──► Consulta a otros microservicios (usuarios, historietas)
   └─────────────┘        └─────────────┘
         │ ▲              ServiceImpl: ejecuta reglas de negocio, validaciones y mapeo DTO ◄─► Modelo
(Model)  ▼ │
   ┌─────────────┐
   │ Repository  │ ──► Interfaz Spring Data JPA: acceso y operaciones sobre la base de datos
   └─────────────┘
         │ ▲
(SQL)    ▼ │
   ┌─────────────┐
   │   Database  │
   └─────────────┘

* DTO: Objeto transversal utilizado para transferir datos limpios entre capas sin exponer el Modelo o facilitar el cuerpo en las peticiones.
```
---
## Uso de RestClient en este microservicio

Esta api guarda solo el **id** de usuarios e historietas (referencias lógicas), porque esos datos pertenecen a otros microservicios. Para mostrar información real (nombre del usuario, nombre y precio de la historieta) se usa `RestClient` de Spring, que hace peticiones HTTP `GET` a esos servicios. Se aplica en tres pasos:

1. **Configuración (`RestClientConfig`)**: define dos beans `RestClient`, cada uno con su URL base:
   * `usuariosRestClient` → `http://localhost:8080/api/usuariosApi`
   * `historietaRestClient` → `http://localhost:8083/api/historietaRequest`
2. **Clientes (`UsuarioClient` y `HistorietaClient`)**: componentes que inyectan el `RestClient` correspondiente y exponen un método de consulta por id (`getUsuarioById` y `obtenerHistorietaPorId`). Si el servicio externo no responde o el recurso no existe, devuelven `null` en vez de lanzar un error.
3. **Uso en los `ServiceImpl`**: el servicio llama al cliente, recibe un DTO externo (`UsuarioExternoDTO` o `HistorietaExternoDTO`, en `DTO/RestClientDTO`) y lo combina con los datos locales.

| Cliente | Dónde se usa | Para qué |
|---|---|---|
| `UsuarioClient` | `CarritoServiceImpl`, `ListaDeseosServiceImpl` | Agregar los datos del usuario al DTO de respuesta |
| `UsuarioClient` | `HistorialComprasServiceImpl` | Validar que el usuario exista antes de crear su historial |
| `HistorietaClient` | `ProductoCarritoServiceImpl` | Obtener el nombre y precio de la historieta al agregarla al carrito |
| `HistorietaClient` | `BoletaServiceimpl` | Obtener el nombre de cada producto para el detalle de la boleta |

> Para que estas consultas funcionen, los microservicios de usuarios (puerto 8080) e historietas (puerto 8083) deben estar levantados.

---
## Endpoints

| Recurso | Ruta base | Métodos |
|---|---|---|
| Carritos | `/api/carritosApi` | GET, POST, PUT, DELETE (también `DELETE /usuario/{id_usuario}`) |
| Productos del carrito | `/api/ProductoCartRequest` | GET, POST, PUT, DELETE |
| Listas de deseos | `/api/ListaDeseosApi` | GET, POST, PUT, DELETE (también `DELETE /usuario/{id_usuario}`) |
| Productos de la lista de deseos | `/api/productoListaDeseosRequest` | GET, POST, DELETE |
| Medios de pago | `/api/mediosPagoApi` | GET, POST, PUT, DELETE |
| Boletas | `/api/boletas` | GET, POST, DELETE |
| Historial de compras | `/api/historialCompras` | GET (también `GET /usuario/{id_usuario}`), POST |

Notas:

* Al crear una **boleta** (`POST /api/boletas/`) se debe enviar `id_usuario`, `id_historial`, `id_medio_pago`, `id_carrito`, `rut_comprador` y `nombre_comprador`. El historial debe existir y pertenecer al mismo usuario. El monto total y la fecha se calculan automáticamente a partir del carrito.
* Al crear un **historial de compras** (`POST /api/historialCompras/`) solo se envía `id_usuario`. No requiere una compra previa y cada usuario puede tener un solo historial.

---
# Dependencias (pom)

* Spring Boot Starter Data JPA
* Spring Boot Starter RestClient
* Spring Boot Starter WebMVC
* MySQL Connector/J
* Project Lombok
* Spring Boot Starter Data JPA Test
* Spring Boot Starter RestClient Test
* Spring Boot Starter WebMVC Test
