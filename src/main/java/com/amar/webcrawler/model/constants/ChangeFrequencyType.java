package com.amar.webcrawler.model.constants;

import java.util.HashMap;
import java.util.Map;

public enum ChangeFrequencyType {
    ALWAYS("always"), HOURLY("hourly"), DAILY("daily"), WEEKLY("weekly"), MONTHLY(
                    "monthly"), YEARLY("yearly"), NEVER("never");

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
