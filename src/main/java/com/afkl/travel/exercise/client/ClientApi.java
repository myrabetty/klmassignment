package com.afkl.travel.exercise.client;

import com.afkl.travel.exercise.controller.UserLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApi.class);
    private final ClientConfiguration clientConfiguration;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientApi(ClientConfiguration clientConfiguration, ObjectMapper objectMapper) {
        this.clientConfiguration = clientConfiguration;
        this.objectMapper = objectMapper;
    }

    public void printAllAirports() {

        try {
            String type = "country";
            String code = "US";
            String json_string = executeGetRequest(clientConfiguration.getBaseUrl());
            final List<UserLocation> userLocations = objectMapper.readValue(json_string, new TypeReference<List<UserLocation>>() {
            });

            //first retrieve the cities
            List<String> cityCodes =  userLocations.stream().filter(x -> code.equals(x.getParentCode()) && type.equals(x.getParentType())).map(x-> x.getCode()).collect(Collectors.toList());

            //then retrieve all airports per city
            List<UserLocation> airports = userLocations.stream().filter( x -> cityCodes.contains(x.getParentCode()) && "airport".equals(x.getType())).collect(Collectors.toList());

            airports.stream().forEach(x ->
            {
                try {
                    System.out.println(objectMapper.writeValueAsString(x));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException ex) {
            LOGGER.error("something wet wrong, ", ex.getMessage());
        }
    }

    private String executeGetRequest(String request) throws IOException {

        String username = clientConfiguration.getUsername();
        String password = clientConfiguration.getPassword();
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build()) {

            HttpGet httpGet = new HttpGet(request);
            httpGet.setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);

            try (CloseableHttpResponse response = client.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity());
            }
        }
    }
}
