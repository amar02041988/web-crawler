package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.constants.ChangeFrequencyType;
import com.amar.webcrawler.model.constants.HtmlTagType;

import java.util.Date;

public interface SiteMapUrl {

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

    public HtmlTagType getType();
}
