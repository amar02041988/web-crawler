package com.amar.webcrawler.model.bo.impl;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.SiteMapResult;

import java.util.Set;

public class SiteMapResultImpl implements SiteMapResult {

    private static final long serialVersionUID = -5322188592328716406L;
    private final Set<SiteMapEntry> allSiteMapEntries;
    private final Set<SiteMapEntry> anchorSiteMapEntries;
    private final Set<SiteMapEntry> srcSiteMapEntries;
    private final Set<SiteMapEntry> linkSiteMapEntries;

    public SiteMapResultImpl(Set<SiteMapEntry> allSiteMapEntries,
                    Set<SiteMapEntry> anchorSiteMapEntries, Set<SiteMapEntry> srcSiteMapEntries,
                    Set<SiteMapEntry> linkSiteMapEntries) {
        super();
        this.allSiteMapEntries = allSiteMapEntries;
        this.anchorSiteMapEntries = anchorSiteMapEntries;
        this.srcSiteMapEntries = srcSiteMapEntries;
        this.linkSiteMapEntries = linkSiteMapEntries;
    }

    @Override
    public Set<SiteMapEntry> getAllSiteMapEntries() {
        return allSiteMapEntries;
    }

    @Override
    public Set<SiteMapEntry> getAnchorSiteMapEntries() {
        return anchorSiteMapEntries;
    }

    @Override
    public Set<SiteMapEntry> getSrcSiteMapEntries() {
        return srcSiteMapEntries;
    }

    @Override
    public Set<SiteMapEntry> getLinkSiteMapEntries() {
        return linkSiteMapEntries;
    }

}
