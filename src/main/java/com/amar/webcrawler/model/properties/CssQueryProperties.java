package com.amar.webcrawler.model.properties;

import com.amar.webcrawler.config.AppConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * Used Spring map based binding to map the values from properties file to the maps in this bean automatically
 * based on naming conventions defined in properties file.
 * Spring configuration for this auto binding is maintained in {@link AppConfig} class.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public final class CssQueryProperties {

    private Map<String, String> anchorQueries = new HashMap<>();
    private Map<String, String> srcQueries = new HashMap<>();
    private Map<String, String> linkQueries = new HashMap<>();

    public CssQueryProperties() {
        super();
    }

    public CssQueryProperties(Map<String, String> anchorQueries, Map<String, String> srcQueries,
                    Map<String, String> linkQueries) {
        super();
        this.anchorQueries = anchorQueries;
        this.srcQueries = srcQueries;
        this.linkQueries = linkQueries;
    }

    public Map<String, String> getAnchorQueries() {
        return anchorQueries;
    }


    public void setAnchorQueries(Map<String, String> anchorQueries) {
        this.anchorQueries = anchorQueries;
    }


    public Map<String, String> getSrcQueries() {
        return srcQueries;
    }


    public void setSrcQueries(Map<String, String> srcQueries) {
        this.srcQueries = srcQueries;
    }


    public Map<String, String> getLinkQueries() {
        return linkQueries;
    }


    public void setLinkQueries(Map<String, String> linkQueries) {
        this.linkQueries = linkQueries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("anchorQueries", anchorQueries).append("srcQueries", srcQueries)
                        .append("linkQueries", linkQueries).toString();
    }

}
