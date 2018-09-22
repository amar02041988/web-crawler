package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.ChangeFrequencyType;
import com.amar.webcrawler.model.SiteMapEntryType;

import java.util.Date;

public interface SiteMapEntry {

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

    public SiteMapEntryType getType();
}
