package com.tests.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tests.demo.entity.NewsEntity;
import com.tests.demo.repository.NewsEntityRepository;


@Service
public class NewsEntityServiceImpl implements NewsEntityService{

    @Autowired
    private NewsEntityRepository newsEntityRepository;

    @Override
    public void addNewsEntity(NewsEntity newsEntity) {
        newsEntityRepository.addNewsEntity(newsEntity);
    }

    @Override
    public String getNewsEntityTitleById(int index) {
        return newsEntityRepository.getNewsEntityTitleById(index);
    }
    
}
