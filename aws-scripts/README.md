# Ems local setup

This project is for local setup of all the dependent services used by EMS using docker-compose.

## Getting Started

https://marketaxess.atlassian.net/browse/EMS2-74


### Prerequisites

1. Install Docker in the windows machine, please refer to - https://marketaxess.atlassian.net/wiki/spaces/EMS/pages/37912581/Docker+Setup
 
1. Point your VM to the assigned docker box, please refer to this - https://wiki.marketaxess.com/pages/viewpage.action?spaceKey=AWW&title=Docker+with+Dedicated+Docker+VM 

### Installing

1. Checkout ems poc project 
``` git checkout ssh://git@bitbucket.vip.marketaxess.com:7999/ems/ems-poc.git ```
1. Go inside the ems-scripts folder
1. Right click and start git bash
```
run command: docker-compose up
```

## Testing services

1. LocalStack Dashboard : ```http://<Docker-Box-IP Address>:8080/#/infra```
2. ActiveMQ Dashboard : ```http://<Docker-Box-IP Address>:8161/admin/```
3. PG Admin GUI : ```http://<Docker-Box-IP Address>:5050/browser/```


## Built With

* Docker compose


## Versioning

We use bitbucket for versioning. 





 
