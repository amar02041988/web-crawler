package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.model.constants.HtmlTagType;

import java.util.Map;
import java.util.Set;

public interface SiteMapTracker<T> {

    public boolean addToSiteMap(T siteMapEntry);

    public boolean isAlreadyInSiteMap(T siteMapEntry);

    public Map<HtmlTagType, Set<T>> getSiteMapUrlSetLookup();

}
