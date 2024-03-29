package com.tests.demo.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.demo.entity.NewsEntity;
import com.tests.demo.repository.NewsEntityRepositoryInterface;
import com.tests.demo.service.NewsEntityService;
import com.tests.demo.utility.Crawler;


@RestController
public class NewsEntityController {

    @Autowired
    private NewsEntityService newsEntityService;

    @Autowired
    private NewsEntityRepositoryInterface newsEntityRepositoryInterface;

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
    public ResponseEntity<String> getNewsEntityFromFlask() throws JsonMappingException, JsonProcessingException {
        String apiFlaskUrl = "http://localhost:5000/receive_flask_send_java";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiFlaskUrl, HttpMethod.GET, null, String.class);
        if(response.getStatusCode()==HttpStatus.OK) {
            String body = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(body);
            String newsEntityTitle = node.get("title").asText();

            NewsEntity currentNewsEntity = new NewsEntity();
            currentNewsEntity.setTitle(newsEntityTitle);
            

            newsEntityRepositoryInterface.save(currentNewsEntity);

            System.out.println(newsEntityTitle);
        }
        return response;
    }

    //todo --> config file for initialization of RestTemplate objects which is to be injected into NewsEntityController  
    @PostMapping("/send_flask_payload")
    public ResponseEntity<String> sendUrlToFlask3(@RequestBody NewsEntity newsEntity) throws JsonMappingException, JsonProcessingException {
        String apiFlaskUrl = "http://localhost:5000/receive_java_send_java";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        JSONObject jsonObject = new JSONObject();
        headers.setContentType(MediaType.APPLICATION_JSON);

        long id=1;
        while(newsEntityRepositoryInterface.existsById(id)) {
            Optional<NewsEntity> optionalNewsEntity = newsEntityRepositoryInterface.findById(id);
            if(optionalNewsEntity.isPresent()) {
                NewsEntity currentNewsEntity = optionalNewsEntity.get();

                URL extractedUrl = currentNewsEntity.getUrl();

                jsonObject.put("url", extractedUrl);

                HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

                String response = restTemplate.postForObject(apiFlaskUrl, request, String.class);

                currentNewsEntity.setSummarizedContent(response);

                newsEntityRepositoryInterface.save(currentNewsEntity);
                
                id++;
                System.out.println(response);
            }
            else
                id++;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //fun!
    @PostMapping("/crawl")
    public void doCrawling(@RequestBody String originUrl) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(originUrl);
        String extractedOrigin = node.get("url").asText();
        
        Crawler.crawl(1, extractedOrigin, new ArrayList<String>(), newsEntityRepositoryInterface);
    }

}
