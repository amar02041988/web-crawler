package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.constants.ChangeFrequencyType;
import com.amar.webcrawler.model.constants.HtmlTagType;

import java.io.Serializable;
import java.util.Date;

public interface SiteMapEntry extends Serializable {

    public int getDepth();

    public HtmlTagType getType();

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

}
