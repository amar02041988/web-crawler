package com.amar.webcrawler.model.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

public final class CssQueryProperties {

    private Map<String, String> hrefQueries = new HashMap<>();
    private Map<String, String> srcQueries = new HashMap<>();
    private Map<String, String> importLinkQueries = new HashMap<>();

    public CssQueryProperties() {
        super();
    }

    public CssQueryProperties(Map<String, String> hrefQueries, Map<String, String> srcQueries,
                    Map<String, String> importLinkQueries) {
        super();
        this.hrefQueries = hrefQueries;
        this.srcQueries = srcQueries;
        this.importLinkQueries = importLinkQueries;
    }


    public Map<String, String> getHrefQueries() {
        return hrefQueries;
    }


    public void sethrefQueries(Map<String, String> hrefQueries) {
        this.hrefQueries = hrefQueries;
    }


    public Map<String, String> getSrcQueries() {
        return srcQueries;
    }


    public void setsrcQueries(Map<String, String> srcQueries) {
        this.srcQueries = srcQueries;
    }


    public Map<String, String> getImportLinkQueries() {
        return importLinkQueries;
    }


    public void setImportLinkQueries(Map<String, String> importLinkQueries) {
        this.importLinkQueries = importLinkQueries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("hrefQueries", hrefQueries).append("srcQueries", srcQueries)
                        .append("importLinkQueries", importLinkQueries).toString();
    }


}
