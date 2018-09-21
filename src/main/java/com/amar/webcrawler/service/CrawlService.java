package com.amar.webcrawler.service;

import org.springframework.stereotype.Service;

@Service
public interface CrawlService<T> {

    public void crawl(T t);
}
