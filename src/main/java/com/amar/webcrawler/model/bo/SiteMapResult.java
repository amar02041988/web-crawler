package com.amar.webcrawler.model.bo;

import java.io.Serializable;
import java.util.Set;

public interface SiteMapResult extends Serializable {

    public Set<SiteMapEntry> getAllSiteMapEntries();

    public Set<SiteMapEntry> getAnchorSiteMapEntries();

    public Set<SiteMapEntry> getSrcSiteMapEntries();

    public Set<SiteMapEntry> getLinkSiteMapEntries();

}
