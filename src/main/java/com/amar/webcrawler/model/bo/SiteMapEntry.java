package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.constants.ChangeFrequencyType;
import com.amar.webcrawler.model.constants.HtmlTagType;

import java.io.Serializable;
import java.util.Date;

/**
 * Business object specification for maintaining site map related details for every searched url.
 * Every searched url will be recorded and maintained as {@link SiteMapEntry}
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public interface SiteMapEntry extends Serializable {

    public int getDepth();

    public HtmlTagType getType();

    public String getLocation();

    public Date getLastModified();

    public ChangeFrequencyType getChangeFrequency();

    public Double getPriority();

}
