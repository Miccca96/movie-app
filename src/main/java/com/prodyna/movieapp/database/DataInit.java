package com.prodyna.movieapp.database;

import com.prodyna.movieapp.service.InitLoadService;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInit {

    private final InitLoadService jsonDataService;

    @Autowired
    public DataInit(InitLoadService jsonDataService) {
        this.jsonDataService = jsonDataService;
    }

    @PostConstruct
    public void fillDatabase() {
        log.info("Started method for filling database");
        try {
            jsonDataService.fillDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
