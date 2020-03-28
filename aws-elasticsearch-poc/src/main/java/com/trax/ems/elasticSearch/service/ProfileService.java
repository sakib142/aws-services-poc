package com.trax.ems.elasticSearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trax.ems.elasticSearch.bean.MarketDepth;

/*
 * Profile service provide all action with Elastic search AWS
 */

@Service
public class ProfileService {

    @Value("${index.type}")
    String indexType;

    @Value("${index.name}")
    String indexName;

    private RestHighLevelClient client;

    private ObjectMapper objectMapper;

    @Autowired
    public ProfileService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public String createProfileDocument(MarketDepth document) throws Exception {

        UUID uuid = UUID.randomUUID();
        document.setId(uuid.toString());
        System.out.println("Service createProfileDocument document -- " + document.getId());
        Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);
        System.out.println("Service createProfileDocument documentMapper -- " + documentMapper.toString());
        IndexRequest indexRequest = new IndexRequest(indexName, XContentType.JSON.name(), document.getId()).source(documentMapper);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getResult().name();
    }

    public MarketDepth findById(String id) {
        GetRequest getRequest = new GetRequest(indexName, XContentType.JSON.name(), id);
        MarketDepth profileDocument = null;
        GetResponse getResponse;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> resultMap = getResponse.getSource();
            System.out.println("Result map -- " + resultMap);
            profileDocument = objectMapper.convertValue(resultMap, MarketDepth.class);
        } catch (IOException e) {
            System.out.println("Exception while findById -- " + e.getMessage());
            e.printStackTrace();
        }
        return profileDocument;
    }

    public String updateProfile(MarketDepth document) throws IOException {
        MarketDepth resultDocument = findById(document.getId());
        UpdateRequest updateRequest = new UpdateRequest(indexName, XContentType.JSON.name(), resultDocument.getId());
        System.out.println("Service updateProfile Document document -- " + document.getId());
        Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);
        updateRequest.doc(documentMapper);
        System.out.println("Service updateProfile " + updateRequest);
        System.out.println("Service updateProfile documentMapper -- " + documentMapper.toString());
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);

        return updateResponse.getResult().name();
    }

    public String deleteProfileDocument(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, XContentType.JSON.name(), id);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return response.getResult().name();
    }

    public List<MarketDepth> findAll() throws IOException {
        System.out.println("Service List ProfileDocument find all -- ");
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("Service List ProfileDocument find all searchResponse -- " + searchResponse);
        return getSearchResult(searchResponse);
    }

    private List<MarketDepth> getSearchResult(SearchResponse searchResponse) {
        System.out.println("Service List ProfileDocument getSearchResult start ");
        SearchHit[] searchHit = searchResponse.getHits().getHits();
        List<MarketDepth> profileDocuments = new ArrayList<>();
        if (searchHit.length > 0) {
            Arrays.stream(searchHit).forEach(
                    hit -> profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), MarketDepth.class)));
        }
        System.out.println("Service List ProfileDocument getSearchResult end. ");
        return profileDocuments;
    }

}
