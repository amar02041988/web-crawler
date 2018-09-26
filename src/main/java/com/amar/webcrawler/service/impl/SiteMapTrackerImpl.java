package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.service.SiteMapTracker;

import java.util.Map;
import java.util.Set;

public class SiteMapTrackerImpl implements SiteMapTracker<SiteMapEntry> {

    private final Map<HtmlTagType, Set<SiteMapEntry>> siteMapUrlSetLookup;

    public SiteMapTrackerImpl(Map<HtmlTagType, Set<SiteMapEntry>> siteMapUrlSetLookup) {
        super();
        this.siteMapUrlSetLookup = siteMapUrlSetLookup;
    }

    @Override
    public boolean addToSiteMap(SiteMapEntry siteMapEntry) {
        return siteMapUrlSetLookup.get(siteMapEntry.getType()).add(siteMapEntry)
                        && siteMapUrlSetLookup.get(HtmlTagType.ALL).add(siteMapEntry);
    }

    @Override
    public boolean isAlreadyInSiteMap(SiteMapEntry siteMapEntry) {
        return siteMapUrlSetLookup.get(siteMapEntry.getType()).contains(siteMapEntry);
    }

    @Override
    public Map<HtmlTagType, Set<SiteMapEntry>> getSiteMapUrlSetLookup() {
        return siteMapUrlSetLookup;
    }

}
