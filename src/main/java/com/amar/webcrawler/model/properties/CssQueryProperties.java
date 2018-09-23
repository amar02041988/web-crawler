package com.amar.webcrawler.model.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

public final class CssQueryProperties {

    private Map<String, String> pageQueries = new HashMap<>();
    private Map<String, String> mediaQueries = new HashMap<>();
    private Map<String, String> importLinkQueries = new HashMap<>();

    public CssQueryProperties() {
        super();
    }

    public CssQueryProperties(Map<String, String> pageQueries, Map<String, String> mediaQueries,
                    Map<String, String> importLinkQueries) {
        super();
        this.pageQueries = pageQueries;
        this.mediaQueries = mediaQueries;
        this.importLinkQueries = importLinkQueries;
    }


    public Map<String, String> getPageQueries() {
        return pageQueries;
    }


    public void setPageQueries(Map<String, String> pageQueries) {
        this.pageQueries = pageQueries;
    }


    public Map<String, String> getMediaQueries() {
        return mediaQueries;
    }


    public void setMediaQueries(Map<String, String> mediaQueries) {
        this.mediaQueries = mediaQueries;
    }


    public Map<String, String> getImportLinkQueries() {
        return importLinkQueries;
    }


    public void setImportLinkQueries(Map<String, String> importLinkQueries) {
        this.importLinkQueries = importLinkQueries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                        .append("pageQueries", pageQueries).append("mediaQueries", mediaQueries)
                        .append("importLinkQueries", importLinkQueries).toString();
    }

}
