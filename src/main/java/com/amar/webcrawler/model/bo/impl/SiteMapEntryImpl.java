package com.amar.webcrawler.model.bo.impl;

import com.amar.webcrawler.model.ChangeFrequencyType;
import com.amar.webcrawler.model.SiteMapEntryType;
import com.amar.webcrawler.model.bo.SiteMapEntry;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.Objects;

public final class SiteMapEntryImpl implements SiteMapEntry {

    private String location;
    private Date lastModified;
    private ChangeFrequencyType changeFrequency;
    private Double priority;
    private SiteMapEntryType type;

    public SiteMapEntryImpl(String loc) {
        super();
        this.location = loc;
        this.type = SiteMapEntryType.PAGE_URL;
        this.changeFrequency = ChangeFrequencyType.NEVER;
    }

    public SiteMapEntryImpl(String location, Date lastModified, ChangeFrequencyType changeFrequency,
                    Double priority, SiteMapEntryType type) {
        super();
        this.location = location;
        this.lastModified = lastModified;
        this.changeFrequency = changeFrequency;
        this.priority = priority;
        this.type = type;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public ChangeFrequencyType getChangeFrequency() {
        return changeFrequency;
    }

    @Override
    public Double getPriority() {
        return priority;
    }

    @Override
    public SiteMapEntryType getType() {
        return type;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SiteMapEntryImpl)) {
            return false;
        }
        SiteMapEntryImpl castOther = (SiteMapEntryImpl) other;
        return Objects.equals(location, castOther.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("location", location)
                        .append("lastModified", lastModified)
                        .append("changeFrequency", changeFrequency).append("priority", priority)
                        .append("type", type).toString();
    }

}
