package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.constants.ChangeFrequencyType;
import com.amar.webcrawler.model.constants.UrlType;

import java.util.Date;

public interface SiteMapUrlEntry {

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

    public UrlType getType();
}
