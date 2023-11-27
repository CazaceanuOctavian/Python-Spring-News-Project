package com.tests.demo;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.tests.demo.service.ShowService;

@RestController
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/show_data/{id}")
    public ResponseEntity<String> getShowById(@PathVariable int id) {
        return new ResponseEntity<String>(showService.getShowTitleById(id), HttpStatus.FOUND);
    }

    @PostMapping("/receive_data")
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        showService.addShow(show);
        return new ResponseEntity<Show>(show, HttpStatus.CREATED);
    }

    @GetMapping("/receive_flask_payload")
    public ResponseEntity<String> getShowFromFlask() {
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
