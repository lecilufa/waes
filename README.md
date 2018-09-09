# Spring Boot + Mybatis + H2 DB + Maven
H2 DB start up with Spring Boot start up, it shutdown as Spring Boot shutdown as well.<br><br>

To run the project,two ways <br>
1. mvn clean package to build a jar, run this jar<br>
2. mvn spring-boot:run<br>

# DataBase
DB name: H2  (in memory DB)<br>
DB site: http://www.h2database.com/html/quickstart.html<br>

DB access URL: http://localhost:8080/h2-console/<br>

Driver Class: org.h2.Driver<br>
JDBC URL : jdbc:h2:mem:test<br>
User Name : sa<br>
Password : none<br>

# Table Design
SQL file: /resources/db/schema.sql<br>

# ORM framework
Use Mybatis<br><br>
