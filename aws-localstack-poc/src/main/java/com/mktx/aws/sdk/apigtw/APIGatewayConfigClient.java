package com.mktx.aws.sdk.apigtw;

import com.amazonaws.HttpMethod;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.*;

public class APIGatewayConfigClient {

    private final static AmazonApiGateway apiGTW = AmazonApiGatewayClientBuilder.standard().withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration("http://10.8.20.84:4567", "us-east-1")).build();

    public static void main(String[] args) {
        APIGatewayConfigClient test = new APIGatewayConfigClient();
        test.creatAndDeployeAPI();
    }

    private void creatAndDeployeAPI() {
        CreateRestApiResult apiResult = createRestApi();

        GetResourcesResult resourcesResult = getResources(apiResult.getId());

        if (resourcesResult == null) {
            System.out.println("Exiting...");
            return;
        }

        CreateResourceResult createResourceResult = createResource(apiResult, resourcesResult);

        PutMethodResult putMethodResult = putMethod(apiResult, createResourceResult);

        PutIntegrationResult putIntegrationResult = putIntegration(apiResult, createResourceResult);

        CreateDeploymentResult deployment = createDeployment(apiResult);

        System.out.println();

        System.out.println(apiResult);

        System.out.println(resourcesResult);

        System.out.println(createResourceResult);

        System.out.println(putMethodResult);

        System.out.println(putIntegrationResult);

        System.out.println(deployment);
    }

    /**
     *
     * @return
     *
     *         Creates a new RestApi resource.
     */
    private CreateRestApiResult createRestApi() {
        CreateRestApiRequest request = new CreateRestApiRequest();
        request.setName("My First API(SDK");
        request.setDescription("This is my first API Created from SDK");
        return apiGTW.createRestApi(request);
    }

    /**
     * @param restApiId
     * @return
     *
     *         Lists information about a collection of Resource resources.
     */
    private GetResourcesResult getResources(String restApiId) {
        GetResourcesRequest request = new GetResourcesRequest();
        request.setRestApiId(restApiId);
        return apiGTW.getResources(request);
    }

    /**
     * @param restApiResult
     * @param resourcesResult
     * @return
     *
     *         Creates a Resource resource.
     */
    private CreateResourceResult createResource(CreateRestApiResult restApiResult, GetResourcesResult resourcesResult) {
        CreateResourceRequest request = new CreateResourceRequest();

        if (resourcesResult.getItems().isEmpty()) {
            System.out.println("No Resources Found");
            return null;
        }
        request.setParentId(resourcesResult.getItems().get(0).getId());
        request.setRestApiId(restApiResult.getId());
        request.setPathPart("instruments");
        return apiGTW.createResource(request);
    }

    /**
     * @param restApiResult
     * @param resourceResult
     * @return
     *
     *         Add a method to an existing Resource resource.
     */
    private PutMethodResult putMethod(CreateRestApiResult restApiResult, CreateResourceResult resourceResult) {
        PutMethodRequest request = new PutMethodRequest();
        request.setHttpMethod(HttpMethod.GET.name());
        request.setResourceId(resourceResult.getId());
        request.setRestApiId(restApiResult.getId());
        request.setAuthorizationType("NONE");
        return apiGTW.putMethod(request);
    }

    /**
     *
     * @param restApiResult
     * @param resourceResult
     * @return
     *
     *         Sets up a method's integration.
     */
    private PutIntegrationResult putIntegration(CreateRestApiResult restApiResult,
            CreateResourceResult resourceResult) {
        PutIntegrationRequest request = new PutIntegrationRequest();
        request.setRestApiId(restApiResult.getId());
        request.setResourceId(resourceResult.getId());
        request.setHttpMethod(HttpMethod.GET.name());
        request.setType(IntegrationType.HTTP);
        request.setUri("http://10.8.20.84:4571");
        request.setHttpMethod(HttpMethod.GET.name());
        request.setContentHandling(ContentHandlingStrategy.CONVERT_TO_TEXT);

        /**
         * For Integrating with Lambda
         */
        /*request.setHttpMethod(HttpMethod.POST.name());
        request.setType(IntegrationType.AWS);
        request.setIntegrationHttpMethod(HttpMethod.POST.name());
//        arn:aws:lambda:us-east-1:000000000000:function:api-test-lambda is the FunctionArn of the Lambda Created
        request.setUri("arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/arn:aws:lambda:us-east-1:000000000000:function:api-test-handler/invocations");
        request.setPassthroughBehavior("WHEN_NO_MATCH"); */


        return apiGTW.putIntegration(request);
    }

    /**
     *
     * @param restApiResult
     * @return
     *
     *         Creates a Deployment resource, which makes a specified RestApi callable over the internet.
     */
    private CreateDeploymentResult createDeployment(CreateRestApiResult restApiResult) {
        CreateDeploymentRequest request = new CreateDeploymentRequest();
        request.setRestApiId(restApiResult.getId());
        request.setStageName("test");
        return apiGTW.createDeployment(request);
    }
}
