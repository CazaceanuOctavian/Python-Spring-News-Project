package com.tests.demo.service;

import com.tests.demo.entity.Show;

public interface ShowService {
    public void addShow(Show show);
    public String getShowTitleById(int index);
}
