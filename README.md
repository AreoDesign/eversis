This application presents solution to recruiting task:

# Space Agency Data Hub

This application was developed with following technology stack:
- Java 8 as OO programming language
- Spring Boot 2 to run Spring Web Application
- Hibernate 5 ORM for data mapping
- Swagger 2 for presentation layer
- H2 embedded database
- Spring modules:
    - Security (for basic HTTP authentication),
    - Data (to access to database layer)
    - Web (for creating web application)
- Libraries: Lombok, Apache Commons.

# How to start

For ease of use of the application Swagger 2 was chosen for nice UX.
When the application is started (public static void main method from SpaceAgencyDataHubApplication.class)
please copy and paste the URL: http://localhost:8080/swagger-ui.html# into web browser.
Then you will be exposed for all available RESTcontroller methods:
- Mission Controller might be divided onto 2 functional groups:
    - WRITE functions (add/edit/remove) accessible only for Content Manager role
    - READ functions: get one and get all missions available for Content Manager and Customer
- Product Controller might be divided onto 2 functional groups:
    - WRITE functions (add/remove) accessible only for Content Manager role
    - READ functions: (get one/get all) products available for Content Manager and Customer.
    NOTICE: different showing style is used to present data - product's url is presented to Customer,
     only when the product was bought and this is fact is stored into order table.
- Order Controller is exposed to allow making orders of products to Customer and is available only for Customer role.

In order to authenticate for particular role and allow respective functionality there were 2 roles predefined:
- For Customer:
    - username: customer
    - password: customer
- For Content Manager:
    - username: manager
    - password: manager

For ease of testing and simulation of a real database 3 missions and 7 products where predefined.
All these data are created from scratch with time application restart.
Despite the fact Swagger presentation layer with 'getAll' records is delivered, H2 database is available for all roles.
H2 database is available under the URL: http://localhost:8080/h2/
Data source access is configured with application.properties file.
To have an access to H2 console you need to pass:
- JDBC URL: jdbc:h2:mem:space_hub
- User Name: areo
- Password: a

# Further development steps and findings

- preliminary work took too much time
- first approach to create robust, light and optimal solution to a given problem resulted in too much time consumption
- not everything was tested, but first assumptions took unit tests into account
- subsequent work lead to work quality deterioration
- missing knowledge resulted into exceeded time consumption and lots of new stuff learnt
- in near future refactor will be done (some comments were left for that)
