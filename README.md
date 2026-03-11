# Personal-Budget-Manager

Es un proyecto backend desarrollado con Spring Boot 4 y Java 21. Esta API RESTful permite a los usuarios gestionar sus finanzas personales mediante el seguimiento de sus ingresos y egresos, garantizando la seguridad de los datos a través de una arquitectura de seguridad con Spring Security + JWT y un control de acceso por roles (RBAC).


## Funcionalidades Principales.
-	Autenticación Stateless (JWT): Implementación de JSON Web Tokens con cifrado BCrypt para un flujo de registro y login seguro y sin estado.

-	Autorización (RBAC): Implementación de permisos basado en roles.  El USER gestiona sus finanzas, mientras que el ADMIN tiene acceso a la supervisión global de usuarios.

-	Gestión Financiera Segura: CRUD completo de transacciones (ingresos / gastos) con aislamiento de datos; cada usuario solo accede a su propia información.

-	Manejo de Excepciones Centralizado: Uso de un GlobalExceptionHandler que intercepta errores de seguridad y excepciones de negocio, devolviendo respuestas JSON útiles.

-	Validación Estricta de Negocio: Protección contra entradas de datos inválidas o montos negativos mediante Jakarta Bean Validation.


## Tecnologías Implementadas
-	Lenguaje: Java 21
-	Framework: Spring Boot 4
-	Seguridad: Spring Security 7 + JWT (JSON Web Token)
-	Persistencia: Spring Data JPA
-	Base de Datos: Oracle DB
-	Validación: Jakarta Bean Validation.

