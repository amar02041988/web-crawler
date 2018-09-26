package com.amar.webcrawler.model.bo;

import java.io.Serializable;
import java.util.Set;

/**
 * Business object specification to provide consolidated details about all and specific site map entry.
 * It provides separate {@link Set} that is used to fetch granular details like metrics for
 * each Set of entry based on their types. 
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public interface SiteMapResult extends Serializable {

    /**
     * Maintains all urls as {@link SiteMapEntry} which are searched and crawled further.
     * @return {@link Set}
     */
    public Set<SiteMapEntry> getAllSiteMapEntries();

    /**
     * Maintains urls that were part of anchor tag in html as {@link SiteMapEntry}.
     * @return {@link Set}
     */
    public Set<SiteMapEntry> getAnchorSiteMapEntries();

    /**
     * Maintains urls that were part of src attribute in html as {@link SiteMapEntry}.
     * @return {@link Set}
     */
    public Set<SiteMapEntry> getSrcSiteMapEntries();

    /**
     * Maintains urls that were part of link tag in html as {@link SiteMapEntry}.
     * @return {@link Set}
     */
    public Set<SiteMapEntry> getLinkSiteMapEntries();

}
