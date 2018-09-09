## Technologies used in the project
* Oracle/Open JDK 1.8
* Maven for build automation.
* SpringBoot for REST endpoints and dependency injection.
* Mybatis for persistence.
* JUnit4 for unit testing.
* Rest Assured for integration testing.
* Jacoco for testing coverage report
* H2 DB for in memory DB

## How to run
To run the project,2 ways <br>
* mvn clean package to build a jar, run this jar<br>
* mvn spring-boot:run<br>

## DataBase
DB name: H2  (in memory DB)<br>
DB site: http://www.h2database.com/html/quickstart.html<br>

DB access URL: http://localhost:8080/h2-console/<br>

Driver Class: org.h2.Driver<br>
JDBC URL : jdbc:h2:mem:test<br>
User Name : sa<br>
Password : none<br>

## Table Design
SQL file: /resources/db/schema.sql<br>

## Unit Test and Integration test
All the testing are in /src/test/java,all the service method have been tested.<br><br>

waes.test.integration: Integration test by Rest Assure<br>
waes.test.rest		 : Unit test of Rest layer<br>
waes.test.service    : Unit test of service layer<br>

## See Testing coverage report(90%)

1.mvn clean package<br>
2.open /target/jacoco-ut/index.html in browser to see coverage report<br>

## How to use API
* (host)/v1/diff/(ID)/left

eg.<br>
URL: http://localhost:8080/v1/diff/1/left<br>
HTTP Method : PUT<br>

Request Body:

It will be like Below JSON format,but in encoded by BASE64

{<br>
	"id":1,<br>
	"leftText":"0123456789123456"<br>
}<br>











