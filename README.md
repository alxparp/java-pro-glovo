# Java Pro Glovo

It is educational project.

The purpose of this project is to create a simple Spring Boot application with a correct structure that contains "repository", 
"services", "controllers", "configuration" etc.

The application uses 2 entities ("Order" and "Product") that are connected to each other by a many-to-many connection and keep 
information about orders and products. For data manipulation was created "glovo" db and was implemented the configuration to connect to it.

The "Repository" directory contains such components as DAO, mappers, queries, and also the implementation of DAO, where the 
CRUD operations are implemented.

The services turn to DAO. A lot of them perform several operations to DB simultaniously, for this were used the transactions.

RestControllers provide access to application functionality to other users. If appear some mistakes, then the methods of 
RestExceptionHandler class are activated for processing them.

Also the HTML-document has been added, which show the information about orders.


## Technologies

* Java SE
* PostgreSQL
* Spring Boot
    - Data JDBC
    - Web
* SQL (DDL, DML, DQL)
* Maven
* GIT
* HTML
* REST
* Thymeleaf
