# Project Title

ems-liquibase-poc

## Getting Started

Agenda of this POC is to demonstrate the usage of liquibase.

Follow below steps to create a spring boot application which connects to the postgres instance and execution of liquibase scripts on application startup.


1. Check out the project.

2. Replace below properties in application.properties file

```
 spring.datasource.url=jdbc:postgresql://<Docker IP>:5432/<db name>
 spring.datasource.jdbc-url=jdbc:postgresql://<Docker IP>:5432/<db name>
```
3. Do maven clean install

4. In order to update the DB changes, use below command in command prompt at the path as root folder of your project

```
  mvn liquibase:update
```
For rollback, use below command in command prompt at the path as root folder of your project
   
```
 mvn liquibase:rollback -Dliquibase.rollbackTag=tag2.0
```
   
  Note -: Here the value of property -Dliquibase.rollbackTag is the value of  tag attribute of tagDatabase.




### Prerequisites

A docker compose file must be ready to create a postgres database.
Follow the below link to get docker compose file



 
