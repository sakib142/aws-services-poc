# Project Title

ems-amq-poc

## Getting Started

This POC is all about creating ActiveMQ broker using a docker compose file and to access queues and topics.

Kindly follow the steps mentioned in below link to create a ActiveMQ broker using a docker compose file.

https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-scripts

Follow below steps to create a spring boot application which connects to the activemq broker and perform operations with queue and topics using camel.

1. Check out the project.

2. Replace below property with your docker Ip address in application.properties file

spring.activemq.broker-url=tcp://<Docker IP>:61616

3. Create a source queue in order to begin the operation with the input.

4. When you run the application Target queue and topic will be automatically created.

5. Do maven clean install

6. Run the main class

7. Give the input to source queue

8. Observe the outputs in target queue and topic. 



### Prerequisites

A docker compose file must be ready to create ActiveMQ broker.
Follow the below link to get docker compose file

https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-scripts


 
