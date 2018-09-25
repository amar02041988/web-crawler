package com.amar.webcrawler.model.constants;

import java.util.HashMap;
import java.util.Map;

public enum ChangeFrequencyType {
    /**
     * Pre-defined values took it from site map specification. However these are not used currently
     * as part of coding assignment. So for now enum type "NEVER" is used. These enum values can be further used
     * when frequency field fetch functionality will be implemented as a feature addition.
     */
    ALWAYS("always"),
    HOURLY("hourly"),
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly"),
    NEVER("never");

    private final String value;

    private ChangeFrequencyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // String value -> Enum element conversion using static reverse resolver
    static final Map<String, ChangeFrequencyType> resolver = new HashMap<>();
    
    static {
        for (ChangeFrequencyType changeFrequencyType : values()) {
            resolver.put(changeFrequencyType.getValue(), changeFrequencyType);
        }
    }

    public static ChangeFrequencyType fromStringValue(String value) {
        return resolver.get(value);
    }
}
