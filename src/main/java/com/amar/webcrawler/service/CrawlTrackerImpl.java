package com.amar.webcrawler.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CrawlTrackerImpl implements CrawlTracker<String> {

    private final Set<String> crawledUrls = new HashSet<String>();

    public CrawlTrackerImpl() {
        super();
    }

    @Override
    public boolean isCrawled(String url) {
        return crawledUrls.contains(url);
    }

    @Override
    public boolean addCrawled(String url) {
        return crawledUrls.add(url);
    }

}
