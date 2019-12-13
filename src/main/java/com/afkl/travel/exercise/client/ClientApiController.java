package com.afkl.travel.exercise.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ClientApiController {

    private final ClientApi clientApi;

    @Autowired
    public ClientApiController(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    @RequestMapping(value="/trigger")
    public void triggerClient() throws IOException {
        clientApi.printAllAirports();
    }

}
