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
    public AppProperties impo() {
        return new AppProperties();
    }

    @Bean
    @Autowired
    public CrawlTracker<SiteMapUrlEntry> crawlTracker() {
        Set<SiteMapUrlEntry> allCrawledUrlEntries = new LinkedHashSet<>();

        Set<SiteMapUrlEntry> crawledPageUrlEntries = new HashSet<>();
        Set<SiteMapUrlEntry> crawledMediaUrlEntries = new HashSet<>();
        Set<SiteMapUrlEntry> crawledImportLinkUrlEntries = new HashSet<>();

        Map<UrlType, Set<SiteMapUrlEntry>> crawledUrlEntriesSetLookup = new HashMap<>();
        crawledUrlEntriesSetLookup.put(UrlType.PAGE, crawledPageUrlEntries);
        crawledUrlEntriesSetLookup.put(UrlType.MEDIA, crawledMediaUrlEntries);
        crawledUrlEntriesSetLookup.put(UrlType.IMPORT_LINK, crawledImportLinkUrlEntries);
        crawledUrlEntriesSetLookup.put(UrlType.ALL, allCrawledUrlEntries);
        return new CrawlTrackerImpl(crawledUrlEntriesSetLookup);
    }

    @Bean
    @Autowired
    public CrawlService<SiteMapUrlEntry> crawlService(CrawlTracker<SiteMapUrlEntry> crawlTracker,
                    CssQueryProperties cssQueryProperties, AppProperties appProperties) {
        Map<UrlType, Map<String, String>> cssQueryLookup = new HashMap<>();
        cssQueryLookup.put(UrlType.PAGE, cssQueryProperties.getPageQueries());
        cssQueryLookup.put(UrlType.MEDIA, cssQueryProperties.getMediaQueries());
        cssQueryLookup.put(UrlType.IMPORT_LINK, cssQueryProperties.getImportLinkQueries());
        return new CrawlServiceImpl(crawlTracker, cssQueryLookup, appProperties);
    }

}
