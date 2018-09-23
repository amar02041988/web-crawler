package com.amar.webcrawler.service;

import org.springframework.stereotype.Service;

@Service
public interface CrawlTracker<T> {

    public boolean add(T t);
}
