package com.afkl.travel.exercise.client;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApi.class);
    private final ClientConfiguration clientConfiguration;

    @Autowired
    public ClientApi(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }


    public void printAllAirports(){

        try {
            String type = "country";
            String code = "US";
            String json_string = executeGetRequest(clientConfiguration.getBaseUrl() + "/" + type + "/" + code);
            JSONArray result  =  (JSONArray) JSONValue.parse(json_string);



        } catch (IOException ex){
            LOGGER.error("something wet wrong, ", ex.getMessage());
        }
    }

    public String executeGetRequest(String request) throws IOException {


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
