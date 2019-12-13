package com.afkl.travel.exercise.client;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientApi {

    private final ClientConfiguration clientConfiguration;

    @Autowired
    public ClientApi(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    public void executeGetRequest() throws IOException {

        String type = "country";
        String code = "US";
        String username = clientConfiguration.getUsername();
        String password = clientConfiguration.getPassword();
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build()) {

            HttpGet httpGet = new HttpGet(clientConfiguration.getBaseUrl() + "/" + type + "/" + code);
            httpGet.setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);

            try (CloseableHttpResponse response = client.execute(httpGet)) {
                System.out.println(response);
            }
        }
    }
}
