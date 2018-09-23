package com.amar.webcrawler.model.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class AppProperties {

    private int connectAndReadTimeoutInMillis;

    public AppProperties() {
        super();
    }

    public AppProperties(int connectAndReadTimeoutInMillis) {
        super();
        this.connectAndReadTimeoutInMillis = connectAndReadTimeoutInMillis;
    }


    public int getConnectAndReadTimeoutInMillis() {
        return connectAndReadTimeoutInMillis;
    }


    public void setConnectAndReadTimeoutInMillis(int connectAndReadTimeoutInMillis) {
        this.connectAndReadTimeoutInMillis = connectAndReadTimeoutInMillis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                        .append("connectAndReadTimeoutInMillis", connectAndReadTimeoutInMillis)
                        .toString();
    }


}
