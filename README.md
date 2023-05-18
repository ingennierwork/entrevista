# Entrevista Avla
API para entrevista

## Contiene lo siguiente:
* Sign-up para un usuario nuevo
* Login para un usuario
* Obtener un usuario por email
* Update de un usuario
* Delete de un usuario

## Tecnologias utilizadas
* Spring Boot 2.7.10
* Spring Security
* JWT
* H2
* Gradle
* Bcrypt
* Java 11

# Empezando:
* el request para el "sign-up" tiene la siguiente forma:
```json
{
    "name": "Felipe",
    "email": "felipetest@gmail.com",
    "password": "password",
    "phones": [
        {
            "number": 1234567,
            "citycode": 12,
            "contrycode": "56"
        }
    ]
}
```
* El response sera el siguiente si el registro es existoso:
```json
{
    "uuid": "338288fa-2c2d-410a-90d9-5db1dccb9e06",
    "name": "Felipe",
    "email": "felipetest@gmail.com",
    "password": "$2a$10$8px9YNkEnuG4JNCkVggEielcZ6ROVCIcll8nrH4y15PZESDgiGrDS",
    "fechaCreacion": "2023-04-14T14:41:49.960",
    "ultimoLogin": "2023-04-14T14:41:49.908",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIkMmEkMTAkOHB4OVlOa0VudUc0Sk5Da1ZnZ0VpZWxjWjZST1ZDSWNsbDhuckg0eTE1UFpFU0RnaUdyRFMiLCJpYXQiOjE2ODE0OTc3MTAsImV4cCI6MTY4MTQ5Nzc0MH0.F8vfIb13Jp-nbeSrDWfO-fvmYYB9FO7lMTnSnn9O5rA",
    "estaActivo": true
}
```
* Para el "login" el request es el siguiente:
  *Colocar el token generado anteriormente en el header con "Authorization" + "Bearer + token" 
```json
{
    "email":"felipetest@gmail.com",
    "password":"password"
}

```
* El response de ser un usuario registrado:
```json
{
    "id": "338288fa-2c2d-410a-90d9-5db1dccb9e06",
    "created": "2023-04-14T14:41:49.960",
    "lastLogin": "2023-04-14T14:42:08.436",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIkMmEkMTAkOHB4OVlOa0VudUc0Sk5Da1ZnZ0VpZWxjWjZST1ZDSWNsbDhuckg0eTE1UFpFU0RnaUdyRFMiLCJpYXQiOjE2ODE0OTc3MjgsImV4cCI6MTY4MTQ5Nzc1OH0.HIUzvFRxSoitS4QlJ5K-_G3oH95-9ULtqSgpSmp7HFQ",
    "name": "Felipe",
    "email": "felipetest@gmail.com",
    "password": "$2a$10$8px9YNkEnuG4JNCkVggEielcZ6ROVCIcll8nrH4y15PZESDgiGrDS",
    "phones": null,
    "active": true
}
```

