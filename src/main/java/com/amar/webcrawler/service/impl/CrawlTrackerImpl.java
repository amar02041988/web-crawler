package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlTracker;
import com.amar.webcrawler.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CrawlTrackerImpl implements CrawlTracker<SiteMapUrl> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlTrackerImpl.class);
    private final Map<HtmlTagType, Set<SiteMapUrl>> siteMapUrlSetLookup;
    private final AppProperties appProperties;
    private final ReentrantLock reentrantLock;

    public CrawlTrackerImpl(Map<HtmlTagType, Set<SiteMapUrl>> trackedUrlEntriesSetLookup,
                    AppProperties appProperties, ReentrantLock reentrantLock) {
        super();
        this.siteMapUrlSetLookup = trackedUrlEntriesSetLookup;
        this.appProperties = appProperties;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public boolean add(SiteMapUrl siteMapUrl) {
        boolean addedFlag = false;
        try {
            reentrantLock.tryLock(60, TimeUnit.SECONDS);
            addedFlag = siteMapUrlSetLookup.get(siteMapUrl.getType()).add(siteMapUrl)
                            && siteMapUrlSetLookup.get(HtmlTagType.ALL).add(siteMapUrl);
            if (addedFlag) {
                UrlUtils.printSiteMapUrl(siteMapUrl);
            }
            return addedFlag;
        } catch (InterruptedException e) {
            LOGGER.error("{}, interrupted while checking and adding url in tracker: {}",
                            Thread.currentThread().getName(), e.getMessage());
            return addedFlag;
        } finally {
            reentrantLock.unlock();
        }
    }


    public Map<HtmlTagType, Set<SiteMapUrl>> getSiteMapUrlSetLookup() {
        return siteMapUrlSetLookup;
    }


    public AppProperties getAppProperties() {
        return appProperties;
    }


    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

}
