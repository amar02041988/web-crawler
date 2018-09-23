package com.amar.webcrawler.config;

import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.constants.UrlType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.model.properties.CssQueryProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import com.amar.webcrawler.service.impl.CrawlServiceImpl;
import com.amar.webcrawler.service.impl.CrawlTrackerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.jsoup.cssquery")
    public CssQueryProperties cssQueryProperties() {
        return new CssQueryProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.settings")
    public AppProperties appProperties() {
        return new AppProperties();
    }

    @Bean
    @Autowired
    public CrawlTracker<SiteMapUrlEntry> crawlTracker(AppProperties appProperties) {
        Set<SiteMapUrlEntry> allTrackedUrlEntries = new LinkedHashSet<>();

        Set<SiteMapUrlEntry> trackedHrefUrlEntries = new HashSet<>();
        Set<SiteMapUrlEntry> trackedSrcUrlEntries = new HashSet<>();
        Set<SiteMapUrlEntry> trackedImportLinkUrlEntries = new HashSet<>();

        Map<UrlType, Set<SiteMapUrlEntry>> trackedUrlEntriesSetLookup = new HashMap<>();
        trackedUrlEntriesSetLookup.put(UrlType.HREF, trackedHrefUrlEntries);
        trackedUrlEntriesSetLookup.put(UrlType.SRC, trackedSrcUrlEntries);
        trackedUrlEntriesSetLookup.put(UrlType.IMPORT_LINK, trackedImportLinkUrlEntries);
        trackedUrlEntriesSetLookup.put(UrlType.ALL, allTrackedUrlEntries);
        return new CrawlTrackerImpl(trackedUrlEntriesSetLookup, appProperties);
    }

    @Bean
    @Autowired
    public CrawlService<SiteMapUrlEntry> crawlService(CrawlTracker<SiteMapUrlEntry> crawlTracker,
                    CssQueryProperties cssQueryProperties, AppProperties appProperties) {
        Map<UrlType, Map<String, String>> cssQueryLookup = new HashMap<>();
        cssQueryLookup.put(UrlType.HREF, cssQueryProperties.getHrefQueries());
        cssQueryLookup.put(UrlType.SRC, cssQueryProperties.getSrcQueries());
        cssQueryLookup.put(UrlType.IMPORT_LINK, cssQueryProperties.getImportLinkQueries());
        return new CrawlServiceImpl(crawlTracker, cssQueryLookup, appProperties);
    }

}
