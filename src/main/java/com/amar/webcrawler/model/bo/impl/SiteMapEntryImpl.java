package com.amar.webcrawler.model.bo.impl;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.constants.ChangeFrequencyType;
import com.amar.webcrawler.model.constants.HtmlTagType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.Objects;

public final class SiteMapEntryImpl implements SiteMapEntry {

    private static final long serialVersionUID = 2731124608425626360L;
    private int depth;
    private HtmlTagType type;
    private String location;
    private Date lastModified;
    private ChangeFrequencyType changeFrequency;
    private Double priority;

    public SiteMapEntryImpl(int depth, HtmlTagType type, String loc) {
        super();
        this.depth = depth;
        this.type = type;
        this.location = loc;
        this.changeFrequency = ChangeFrequencyType.NEVER;
    }

    public SiteMapEntryImpl(int depth, HtmlTagType type, String location, Date lastModified,
                    ChangeFrequencyType changeFrequency, Double priority) {
        super();
        this.depth = depth;
        this.type = type;
        this.location = location;
        this.lastModified = lastModified;
        this.changeFrequency = changeFrequency;
        this.priority = priority;
    }

    @Override
    public HtmlTagType getType() {
        return type;
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
    public int getDepth() {
        return depth;
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

    public String toStringMinimal() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("depth", depth)
                        .append("type", type).append("location", location).toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("type", type)
                        .append("location", location).append("lastModified", lastModified)
                        .append("changeFrequency", changeFrequency).append("priority", priority)
                        .append("depth", depth).toString();
    }

}
