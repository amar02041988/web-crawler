package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.ChangeFrequencyType;
import com.amar.webcrawler.model.UrlType;

import java.util.Date;

public interface SiteMapUrlEntry {

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

    public UrlType getType();
}
