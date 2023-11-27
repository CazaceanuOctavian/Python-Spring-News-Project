package com.tests.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tests.demo.entity.NewsEntity;
import com.tests.demo.service.NewsEntityService;

@RestController
public class NewsEntityController {

    @Autowired
    private NewsEntityService newsEntityService;

    @GetMapping("/show_data/{id}")
    public ResponseEntity<String> getShowById(@PathVariable int id) {
        return new ResponseEntity<String>(newsEntityService.getNewsEntityTitleById(id), HttpStatus.FOUND);
    }

    @PostMapping("/receive_data")
    public ResponseEntity<NewsEntity> createShow(@RequestBody NewsEntity newsEntity) {
        newsEntityService.addNewsEntity(newsEntity);
        return new ResponseEntity<>(newsEntity, HttpStatus.CREATED);
    }

    @GetMapping("/receive_flask_payload")
    public ResponseEntity<String> getNewsEntityFromFlask() {
        String apiFlaskUrl = "http://localhost:5000/post_example";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiFlaskUrl, HttpMethod.POST, null, String.class);
        if(response.getStatusCode()==HttpStatus.OK) {
            String body = response.getBody();
            System.out.println(body);
        }
        return response;
    }

}
