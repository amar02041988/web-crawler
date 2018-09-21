package com.amar.webcrawler.service;

import org.springframework.stereotype.Service;

@Service
public interface CrawlTracker<T> {

    public boolean isCrawled(T t);

    public boolean addCrawled(T t);
}
