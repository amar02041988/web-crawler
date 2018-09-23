package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.constants.UrlType;
import com.amar.webcrawler.service.CrawlTracker;

import java.util.Map;
import java.util.Set;

public class CrawlTrackerImpl implements CrawlTracker<SiteMapUrlEntry> {

    private final Map<UrlType, Set<SiteMapUrlEntry>> crawledUrlEntriesSetLookup;

    public CrawlTrackerImpl(Map<UrlType, Set<SiteMapUrlEntry>> crawledUrlEntriesSetLookup) {
        super();
        this.crawledUrlEntriesSetLookup = crawledUrlEntriesSetLookup;
    }

    @Override
    public boolean isCrawled(SiteMapUrlEntry siteMapUrlEntry) {
        return crawledUrlEntriesSetLookup.get(siteMapUrlEntry.getType()).contains(siteMapUrlEntry);
    }

    @Override
    public boolean addCrawled(SiteMapUrlEntry siteMapUrlEntry) {
        return crawledUrlEntriesSetLookup.get(siteMapUrlEntry.getType()).add(siteMapUrlEntry)
                        && crawledUrlEntriesSetLookup.get(UrlType.ALL).add(siteMapUrlEntry);
    }
}
