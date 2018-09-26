package com.amar.webcrawler.service;

import com.amar.webcrawler.model.constants.HtmlTagType;

import java.util.Map;
import java.util.Set;

/**
 * Business service specification to be used for writing implementation for site map tracker.
 * It keeps a track of urls that are searched.
 * It also provides a lookup to retrieve {@link Set} of urls that were searched, based on {@link HtmlTagType}.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public interface SiteMapTracker<T> {

    /**
     * Aadd new url as site map entry in tracker
     * 
     * @param siteMapEntry to be added
     * @return true is added else false if already exist
     */
    public boolean addToSiteMap(T siteMapEntry);

    /**
     * 
     * @param siteMapEntry entry to be checked for it's existance in Site map set 
     * @return true if already exist or false if it's a new entry
     */
    public boolean isAlreadyInSiteMap(T siteMapEntry);

    /**
     * Maintains all site map sets based on html tag type 
     * @return map which contains key as {@link HtmlTagType}
     * and value as {@link Set} containing all site map entires having urls that were part of {@link HtmlTagType} in web page.
     */
    public Map<HtmlTagType, Set<T>> getSiteMapUrlSetLookup();

}
