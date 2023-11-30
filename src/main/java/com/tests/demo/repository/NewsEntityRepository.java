package com.tests.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tests.demo.entity.NewsEntity;

@Repository
public class NewsEntityRepository {
    private List<NewsEntity> newsEntityList = new ArrayList<>();

    public void addNewsEntity(NewsEntity newsEntity) {
        newsEntityList.add(newsEntity);
    }

    public String getNewsEntityTitleById(int index) {
        return newsEntityList.get(index).getTitle();
    }
}
