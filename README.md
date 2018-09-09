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
All the tests are in /src/test/java, all the service methods have been tested.<br><br>

waes.test.integration: Integration test by Rest Assure<br>
waes.test.rest		 : Unit test of Rest layer<br>
waes.test.service    : Unit test of service layer<br>

## See Testing coverage report(90%)

1.mvn clean package<br>
2.open /target/jacoco-ut/index.html in browser to see coverage report<br>

## How to use API
**1. (host)/v1/diff/(ID)/left**

Store left text to database if text with given id do not exist.<br>
If existed, it will update left text.<br>
It will return the saved text in JSON format

eg.<br>
URL: http://localhost:8080/v1/diff/1/left<br>
HTTP Method : PUT<br>

Request Body:

It will be like Below JSON format,but in encoded by BASE64

{<br>
	"id":1,<br>
	"leftText":"0123456789123456"<br>
}<br>

Response Body(normal):<br>
{<br>
	"id":1,<br>
	"leftText":"0123456789123456"<br>
}<br>

Response Body(request body incorrect):<br>
{<br>
    "exception": "com.fasterxml.jackson.core.JsonParseException",<br>
    "message": "Malformed REST request",<br>
    "code": 400<br>
}<br>


**2. (host)/v1/diff/(ID)/right**

Store right text to database if text with given id do not exist.<br>
If existed, it will update right text<br>
It will return the saved text in JSON format

eg.<br>
URL: http://localhost:8080/v1/diff/1/right<br>
HTTP Method : PUT<br>

Request Body:

It will be like Below JSON format,but in encoded by BASE64

{<br>
	"id":1,<br>
	"rightText":"0123456789**3456"<br>
}<br>


Response Body(normal):<br>
{<br>
	"id":1,<br>
	"rightText":"0123456789**3456"<br>
}<br>

Response Body(request body incorrect):<br>
{<br>
    "exception": "com.fasterxml.jackson.core.JsonParseException",<br>
    "message": "Malformed REST request",<br>
    "code": 400<br>
}<br>


**3. (host)/v1/diff/(ID)**

The diff-ed result of left and right text with given id.

eg.<br>
URL: http://localhost:8080/v1/diff/13<br>
HTTP Method : GET<br>


**EQUAL**<br>
Response Body:<br>
{<br>
    "id": 13,<br>
    "status": "EQUAL",<br>
    "diffs": []<br>
}<br>

**NOT_SAME_SIZE**<br>
Response Body:<br>
{<br>
    "id": 14,<br>
    "status": "NOT_SAME_SIZE",<br>
    "diffs": []<br>
}<br>


**DIFF**<br>
Noticed: The offset begins from 0, not 1; length begins from 1.<br>
Response Body:<br>
{<br>
    "id": 13,<br>
    "status": "DIFF",<br>
    "diffs": [<br>
        {"offset": 0, "length": 1},<br>
        {"offset": 4, "length": 2},<br>
        {"offset": 13,"length": 2}<br>
    ]<br>
}<br>


**Text not existed**<br>
Response Body:<br>
{<br>
    "exception": "waes.task.exception.PreconditionException",<br>
    "message": "No text with given id",<br>
    "code": 428<br>
}<br>


**Left text missing**<br>
Response Body:<br>
{<br>
    "exception": "waes.task.exception.PreconditionException",<br>
    "message": "Left text should not be null",<br>
    "code": 428<br>
}<br>

**Right text missing**<br>
Response Body:<br>
{<br>
    "exception": "waes.task.exception.PreconditionException",<br>
    "message": "Right text should not be null",<br>
    "code": 428<br>
}<br>

## Suggestions

*  (host)/v1/diff/(ID)/left

This endpoint can save a text and also update a text,depending on whether text exists.<br>
This make me rather confused whether should use HTTP method POST or PUT <br>
So if possible, should we split it into 2 endpoints POST and PUT<br> 
And assigning ID by rest clients is not so good.<br>

* (host)/v1/diff/(ID)

There are 3 cases: Equal, Not same size, Diff.<br>
I think  Not same size and Diff should be the same case. So it can be just 2 cases: Equal, Diff<br>
For not same size, eg. left = 123456  right= 12**56abc<br>
the outcome should be (offset begins from 0, length begins from 1)<br>

{<br>
	"id":12,<br>
	diffs :[<br>
		{offset:2,length:2},<br>
		{offset:6,length:3}<br>
	]<br>
}<br>

if equal:<br>
{<br>
	"id":12,<br>
	diffs :[]<br>
}<br>


No status needed,only diffs in different cases.<br>













