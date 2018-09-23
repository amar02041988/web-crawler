package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.constants.UrlType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlTracker;

import java.util.Map;
import java.util.Set;

public class CrawlTrackerImpl implements CrawlTracker<SiteMapUrlEntry> {

    private final Map<UrlType, Set<SiteMapUrlEntry>> trackedUrlEntriesSetLookup;
    private final AppProperties appProperties;

    public CrawlTrackerImpl(Map<UrlType, Set<SiteMapUrlEntry>> trackedUrlEntriesSetLookup,
                    AppProperties appProperties) {
        super();
        this.trackedUrlEntriesSetLookup = trackedUrlEntriesSetLookup;
        this.appProperties = appProperties;
    }

    // @Override
    // public boolean isTracked(SiteMapUrlEntry siteMapUrlEntry) {
    // return trackedUrlEntriesSetLookup.get(siteMapUrlEntry.getType()).contains(siteMapUrlEntry);
    // }

    @Override
    public boolean add(SiteMapUrlEntry siteMapUrlEntry) {
        
        return trackedUrlEntriesSetLookup.get(siteMapUrlEntry.getType()).add(siteMapUrlEntry)
                        && trackedUrlEntriesSetLookup.get(UrlType.ALL).add(siteMapUrlEntry);
    }

    
    public Map<UrlType, Set<SiteMapUrlEntry>> getTrackedUrlEntriesSetLookup() {
        return trackedUrlEntriesSetLookup;
    }

    
    public AppProperties getAppProperties() {
        return appProperties;
    }
    
}
