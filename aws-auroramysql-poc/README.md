# Project Title

EMS Order Service: The purpose of this POC to migrate from postgres to mysql database, checking multi-region functionality etc.

## Getting Started

Order Service gives you all the Active Orders for a given Firm ID , which is present in EMS_ORDER table .

Check out the project and follow below steps.

1. create a lambda function to insert and query data from mysql database [Order data].

2. create shaded-jar of the project and upload it to lambda [via S3 or directly]

3. as part of event pass: insert or search to basically insert or search data from mysql.

4. Make sure mysql instance is created and updated into properties files with URL info.

5. also while creating database instance, created read replica to other region so that when insertion happens, it happens all across regions.

6. trigger lambda again with "Search" as string to fetch inserted data from mysql.

### Prerequisites

<li> RDS mysql db instance & updated host and ports in property files.
<li> Make sure 5003 is not blocked, it's where service will listen to.
