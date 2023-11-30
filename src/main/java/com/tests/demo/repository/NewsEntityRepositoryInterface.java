package com.tests.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.tests.demo.entity.NewsEntity;

public interface NewsEntityRepositoryInterface extends CrudRepository<NewsEntity, Long>{
    
}
