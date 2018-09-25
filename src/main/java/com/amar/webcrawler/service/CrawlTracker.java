package com.amar.webcrawler.service;

import org.springframework.stereotype.Service;

@Service
public interface CrawlTracker<T> {

    public boolean isVisited(T url);

    public boolean addVisited(T url);


}
