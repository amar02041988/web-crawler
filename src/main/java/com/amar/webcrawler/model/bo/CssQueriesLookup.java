package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.CssQueries;
import com.amar.webcrawler.model.UrlType;

import java.util.Map;

public final class CssQueriesLookup {

    private final Map<UrlType, CssQueries> cssQueryPropertiesMap;

    public CssQueriesLookup(Map<UrlType, CssQueries> cssQueryPropertiesLookup) {
        super();
        this.cssQueryPropertiesMap = cssQueryPropertiesLookup;
    }

    public Map<UrlType, CssQueries> getUrlTypeBasedCssQueries() {
        return cssQueryPropertiesMap;
    }

}
