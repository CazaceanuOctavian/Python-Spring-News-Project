package com.tests.demo.entity;

import java.net.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "news_entity")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "URL", nullable = true)
    URL url;

    @Column(name = "summarized_content", nullable = true, length = 50000)
    String summarizedContent;

    @Column(name = "title", nullable = true)
    String title;

    public NewsEntity() {

    }

    public NewsEntity(int id, String title, URL url, String summarizedContent) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.summarizedContent = summarizedContent;
    }


    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getSummarizedContent() {
        return this.summarizedContent;
    }

    public void setSummarizedContent(String summarizedContent) {
        this.summarizedContent = summarizedContent;
    }



}
