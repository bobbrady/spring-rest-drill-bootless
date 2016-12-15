# Spring Drill: MVC REST Bootless

This is a Spring drill project.  It is meant as an exercise to review
basic techniques of building a Spring MVC maven project for a RESTful API as an executable jar with embedded tomcat.  It can also be used for tech interview preparation.

Spring Boot is not used here.  Spring Boot abstracts away much of the configuration and thought process of building a RESTful API microservice.  While this is good on-the-job, it will not prepare you for some tech interview scenarios or keep your depth of Sping knowledge intact.

## Features

* Spring 4 MVC
* Tomcat 8 embedded in exectuable jar
* 100% Java Spring Config with annotations
* Modern Spring web app layout: No web.xml, servlet 3.1 tech
* Simple user RESTful API
* Simple thread-safe data repository as ConcurrentHashMap
* Test coverage using Spring MVC Mock, Mockito, and Jayway for JSON validation

## Usage

Try to recreate this project from memory.

```bash
mvn clean complie package
java -jar target/restdrill.jar
curl http://localhost:8080/api/user/1
{"id":1,"name":"FuBar","age":35,"salary":150.00}
````


