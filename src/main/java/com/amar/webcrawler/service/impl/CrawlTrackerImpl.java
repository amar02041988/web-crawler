package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.SiteMapEntryType;
import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.service.CrawlTracker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class CrawlTrackerImpl implements CrawlTracker<SiteMapEntryImpl> {

    // insertion order is important to monitor results based on dept
    private final Set<SiteMapEntryImpl> allCrawledEntries;

    private final Set<SiteMapEntryImpl> crawledPageEntries;
    private final Set<SiteMapEntryImpl> crawledImageEntries;
    private final Set<SiteMapEntryImpl> crawledImportLinkEntries;

    private final Map<SiteMapEntryType, Set<SiteMapEntryImpl>> crawledEntriesLookup;

    public CrawlTrackerImpl() {
        super();
        allCrawledEntries = new LinkedHashSet<>();

        crawledPageEntries = new HashSet<>();
        crawledImageEntries = new HashSet<>();
        crawledImportLinkEntries = new HashSet<>();

        crawledEntriesLookup = new HashMap<>();
        crawledEntriesLookup.put(SiteMapEntryType.PAGE_URL, crawledPageEntries);
        crawledEntriesLookup.put(SiteMapEntryType.IMAGE_URL, crawledImageEntries);
        crawledEntriesLookup.put(SiteMapEntryType.IMPORT_LINK_URL, crawledImportLinkEntries);
    }

    @Override
    public boolean isCrawled(SiteMapEntryImpl siteMapEntryUrl) {
        return crawledEntriesLookup.get(siteMapEntryUrl.getType()).contains(siteMapEntryUrl);
    }

    @Override
    public boolean addCrawled(SiteMapEntryImpl siteMapEntryUrl) {
        return crawledEntriesLookup.get(siteMapEntryUrl.getType()).add(siteMapEntryUrl)
                        && allCrawledEntries.add(siteMapEntryUrl);
    }
}
