package com.prodyna.movieapp.database;

import com.prodyna.movieapp.service.JsonDataService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInit {

    private final JsonDataService jsonDataService;

    @Autowired
    public DataInit(JsonDataService jsonDataService) {
        this.jsonDataService = jsonDataService;
    }

    @PostConstruct
    public void fillDatabase(){
        try {
            jsonDataService.fillDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
