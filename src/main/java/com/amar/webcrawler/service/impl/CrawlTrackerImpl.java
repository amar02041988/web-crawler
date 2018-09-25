package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.service.CrawlTracker;

import java.util.Set;

public class CrawlTrackerImpl implements CrawlTracker<String> {

    private final Set<String> visitedUrlSet;

    public CrawlTrackerImpl(Set<String> visitedUrlSet) {
        super();
        this.visitedUrlSet = visitedUrlSet;
    }

    @Override
    public boolean isVisited(String url) {
        return visitedUrlSet.contains(url);
    }

    @Override
    public boolean addVisited(String url) {
        return visitedUrlSet.add(url);
    }

    public Set<String> getVisitedUrlSet() {
        return visitedUrlSet;
    }
}
