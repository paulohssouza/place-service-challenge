<h1 align="center">
  Place Service Challenge
</h1>

API to manage places (CRUD) that is part of [this challenge](https://github.com/RocketBus/quero-be-clickbus/tree/master/tests/backend-developer) for backend developers applying to ClickBus.

The project was elaborated after the excellent explanation of Giuliana Bezerra [in this vídeo](https://youtu.be/SsWZ4O9iWuo).

[Profile Giuliana Bezerra](https://github.com/giuliana-bezerra)

[Linkedin Giuliana Bezerra](https://www.linkedin.com/in/giulianabezerra/)

## Technologies
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Slugify](https://github.com/slugify/slugify)

## Design patterns

- SOLID
- Automated tests
- Queries with dynamic filters using Query By Example
- Reactive API in the web and database layer
- Using DTOs for the API
- Dependency Injection
- Automatic Swagger generation with OpenAPI 3
- Automatic slug generation with Slugify
- Entity creation and update audit

## How to Run

### Locally
- Clone git repository
- Build the project:
```
./mvnw clean package
```
- Execute:
```
java -jar place-service/target/place-service-0.0.1-SNAPSHOT.jar
```

The API can be accessed at [localhost:8080](http://localhost:8080).
Swagger can be viewed at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Using Docker

- Clone git repository
- Build the project:
```
./mvnw clean package
```
- Build the image:
```
./mvnw spring-boot:build-image
```
- Run the container:
```
docker run --name place-service -p 8080:8080  -d place-service:0.0.1-SNAPSHOT
```

The API can be accessed at [localhost:8080](http://localhost:8080).
Swagger can be viewed at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

To make the HTTP requests below, the tool was used [httpie](https://httpie.io):

- POST /places
```
http POST :8080/places name="Place" state="State"

HTTP/1.1 200 OK
Content-Length: 129
Content-Type: application/json

{
    "createdAt": "2023-04-20T19:00:07.241632",
    "name": "Place",
    "slug": "place",
    "state": "State",
    "updatedAt": "2023-04-20T19:00:07.241632"
}
```

- GET /places/{id}
```
http :8080/places/1
HTTP/1.1 200 OK
Content-Length: 129
Content-Type: application/json

{
    "createdAt": "2023-06-07T14:45:39.693689",
    "name": "Place",
    "slug": "place",
    "state": "State",
    "updatedAt": "2023-06-07T14:45:39.693689"
} 
```

- GET /places?name=?
```
http :8080/places name==PLACE
HTTP/1.1 200 OK
Content-Type: application/json
transfer-encoding: chunked

[
    {
        "createdAt": "2023-06-07T14:45:39.693689",
        "name": "Place",
        "slug": "place",
        "state": "State",
        "updatedAt": "2023-06-07T14:45:39.693689"
    }
]
```

- PATCH /places/{id}
```
http PATCH :8080/places/1 name='New Name' state='New State'
HTTP/1.1 200 OK
Content-Length: 142
Content-Type: application/json

{
    "createdAt": "2023-06-07T14:45:39.693689",
    "name": "New Name",
    "slug": "new-name",
    "state": "New State",
    "updatedAt": "2023-06-07T14:53:21.671129345"
}
```
