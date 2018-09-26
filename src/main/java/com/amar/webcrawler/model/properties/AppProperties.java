package com.amar.webcrawler.model.properties;

import com.amar.webcrawler.config.AppConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used Spring auto binding of values from properties file to this bean.
 * Spring configuration for this auto binding is maintained in {@link AppConfig} class.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public final class AppProperties {

    private int connectAndReadTimeoutInMillis;
    private String domain;
    private int maxDepth;

    public AppProperties() {
        super();
    }

    public AppProperties(int connectAndReadTimeoutInMillis, String domain, int maxDepth) {
        super();
        this.connectAndReadTimeoutInMillis = connectAndReadTimeoutInMillis;
        this.domain = domain;
        this.maxDepth = maxDepth;
    }

    public int getConnectAndReadTimeoutInMillis() {
        return connectAndReadTimeoutInMillis;
    }


    public void setConnectAndReadTimeoutInMillis(int connectAndReadTimeoutInMillis) {
        this.connectAndReadTimeoutInMillis = connectAndReadTimeoutInMillis;
    }

    public String getDomain() {
        return domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("connectAndReadTimeoutInMillis", connectAndReadTimeoutInMillis)
                        .append("domain", domain).append("maxDepth", maxDepth).toString();
    }

}
