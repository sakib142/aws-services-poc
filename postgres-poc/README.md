# Project Title

ems-postgres-poc

## Getting Started

Agenda of this POC is creating a Postgres db using a docker compose file and to perform CRUD operation using postgres instance.

Follow below steps to create a spring boot application which connects to the postgres instance and perform CRUD using spring data jpa.


1. Check out the project.

2. Replace below properties in application.properties file

spring.datasource.url=jdbc:postgresql://<Docker IP>:5432/<db name>
spring.datasource.jdbc-url=jdbc:postgresql://<Docker IP>:5432/<db name>

3. Do maven clean install

3. Run the main class

4. Perform CRUD using Postman or any client app.



### Prerequisites


# AWS-Batch aws-refdata-feed

## Getting Started

Agenda of this POC is creating a Postgres db using a aws console and to read the data(EMS-Instrument) from postgres.
 
Follow below steps to create a spring boot application which connects to the postgres instance and to read the data(EMS-Instrument) from postgres.

1. Check out the project.
   
2. Replace below properties in application.properties file

spring.datasource.url=jdbc:postgresql://<end-point of postgres >/<db name>
spring.datasource.jdbc-url=jdbc:postgresql://<end-point of postgres >/<db name>
spring.datasource.username=*****
spring.datasource.password=******

uncomment below to execute aws batch 
```
spring.datasource.username=
spring.datasource.password=
spring.datasource.url=jdbc:postgresql://aws-refdata.cgryznny3qv3.us-east-2.rds.amazonaws.com/aws_ref_data?verifyServerCertificate=false&useSSL=false
spring.datasource.jdbc-url=jdbc:postgresql://aws-refdata.cgryznny3qv3.us-east-2.rds.amazonaws.com/aws_ref_data?verifyServerCertificate=false&useSSL=false

```

3. Do maven clean install and create a shaded jar to execute lambda and create a function.

4. Provide input like :-

```
{
    "action":"order",
   "emsInstrument":{
    "instrumentId": "02",
      "isIn": "isisn",
      "cusIp": "cusIp",
      "cins": "cins"
     },
     "order":{
    "id": "121",
    "name": "localstack sqs",
    "description": "localstack sqs desc",
    "source": "OMS2",
    "target": "EMS2",
    "venue": "MKTX"
    }
}
```
Note : - 
Action will be "instrument" and db will be "aws_ref_data" to read data of ems-instrument. else action "order " will perform crud operation in "ems" db.
5. Create a compute environment with needs.

6. Create a job queue and job definition .

7. Submit the job to execute.

