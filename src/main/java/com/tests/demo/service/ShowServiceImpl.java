package com.tests.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tests.demo.entity.Show;
import com.tests.demo.repository.ShowRepository;

@Service
public class ShowServiceImpl implements ShowService{

    @Autowired
    private ShowRepository showRepository;

    @Override
    public void addShow(Show show) {
        showRepository.addShow(show);
    }

    @Override
    public String getShowTitleById(int index) {
        return showRepository.getShowTitleById(index);
    }
    
}
