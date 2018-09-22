package com.amar.webcrawler.config;

import com.amar.webcrawler.model.CssQueries;
import com.amar.webcrawler.model.UrlType;
import com.amar.webcrawler.model.bo.ImportLinkCssQueries;
import com.amar.webcrawler.model.bo.MediaCssQueries;
import com.amar.webcrawler.model.bo.PageCssQueries;
import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
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
    @ConfigurationProperties(prefix = "app.cssquery.page")
    public CssQueries pageCssQueries() {
        return new PageCssQueries();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.cssquery.media")
    public CssQueries mediaCssQueries() {
        return new MediaCssQueries();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.cssquery.importlink")
    public CssQueries importLinkCssQueries() {
        return new ImportLinkCssQueries();
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
    public CrawlService<SiteMapUrlEntry> crawlService(CrawlTracker<SiteMapUrlEntry> crawlTracker) {
        Map<UrlType, CssQueries> cssQueryPropertiesMap = new HashMap<>();
        cssQueryPropertiesMap.put(UrlType.PAGE, pageCssQueries());
        cssQueryPropertiesMap.put(UrlType.MEDIA, mediaCssQueries());
        cssQueryPropertiesMap.put(UrlType.IMPORT_LINK, importLinkCssQueries());

        return new CrawlServiceImpl(crawlTracker, cssQueryPropertiesMap);
    }

}
