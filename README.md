Forum API
==================

## Description
This is simple Spring Boot Application, mimics forum behavior. Api allows to create new question,
replay to given question and display all stored questions in form of thread. 
It uses inmemory database so there is no persistence. Always starts with clean database.

## General
* Project use java 1.8 and maven
* To build project execute `mvn clean install`
* To run application execute `mvn spring-boot:run`
* Application is configured to run on port:5000
* Application is using inmemory database

## Documentation
Java Doc is available here: [JavaDoc](javadoc/index.html).

# API Description

Application exposes 4 endpoints:

### Post new question: `http://localhost:5000/questions`

Example request body:

```json
{
  "author": "Daniel",
  "message": "Message text"
}
```

Example Response 201 CREATED:

```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": 0
}
```

### Post a reply to a message: `http://localhost:5000/questions/{questionId}/reply`

Example request body:

```json
{
  "author": "Reply author",
  "message": "Message reply text"
}
```

Example Response 201 CREATED:

```json
{
  "questionId": 1,
  "id": 5,
  "author": "Reply author",
  "message": "Message reply text"
}
```

### Get a thread: `http://localhost:5000/questions/{questionId}`,

Example response:

```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": [
    {
      "id": 5,
      "author": "Reply author",
      "message": "Message reply text"
    },
    ...
  ]
}
```

### Get a list of questions: `http://localhost:5000/questions`

Example response:

```json
[
  {
    "id": 1,
    "author": "Daniel",
    "message": "Message text",
    "replies": 0
  },
  ...
]
```
