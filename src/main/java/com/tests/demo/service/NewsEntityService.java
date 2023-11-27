package com.tests.demo.service;

import com.tests.demo.entity.NewsEntity;

public interface NewsEntityService {
    public void addNewsEntity(NewsEntity newsEntity);
    public String getNewsEntityTitleById(int index);
}
