package com.amar.webcrawler.model.bo;

import com.amar.webcrawler.model.CssQueries;

public class DefaultCssQueries implements CssQueries {

    private String selectUrl;
    private String absoluteUrl;


    public DefaultCssQueries() {
        super();
    }

    public DefaultCssQueries(String selectUrl, String absoluteUrl) {
        super();
        this.selectUrl = selectUrl;
        this.absoluteUrl = absoluteUrl;
    }

    @Override
    public String getSelectUrl() {
        return selectUrl;
    }

    @Override
    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setSelectUrl(String selectUrl) {
        this.selectUrl = selectUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

}
