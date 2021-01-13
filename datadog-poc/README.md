# Configure Microservice with Datadog

Configure your environment and micro service to capture API traces

## Getting Started

### Installing

1. Install Datadog agent for windows OS
2. Install Datadog java agent for running to application
3. Add the datadog.yaml file mentioned in step 12 in above confluence link in the directory C:\ProgramData\Datadog\ 

Note-: Don't forget to change API_KEY in logback.xml file.

## Running microservice 

Run the test microservice with datadog agent  
```
java -javaagent:C:\DatadogAgentjar\dd-java-agent-0.29.1.jar -Ddd.service.name=ems-datadog-poc -jar ems-datadog-poc-0.0.1-SNAPSHOT.jar

```

Once above service runs, Please open the pre-requisite link [DataDog](https://marketaxess.atlassian.net/wiki/spaces/EMS/pages/38765102/Datadog+Setup+And+Configuration) 

you can follow Step 14 onwards under Setup and Configuration Steps in order to see the MS is published and traced/tracks can be found there.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use bitbucket for versioning 

