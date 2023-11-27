package com.tests.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tests.demo.entity.Show;

@Repository
public class ShowRepository {
    private List<Show> shows = new ArrayList<>();

    public void addShow(Show show) {
        shows.add(show);
    }

    public String getShowTitleById(int index) {
        return shows.get(index).getTitle();
    }
}
