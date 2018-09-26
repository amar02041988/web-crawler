package com.amar.webcrawler.config;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.model.properties.CssQueryProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import com.amar.webcrawler.service.SiteMapTracker;
import com.amar.webcrawler.service.impl.CrawlServiceImpl;
import com.amar.webcrawler.service.impl.CrawlTrackerImpl;
import com.amar.webcrawler.service.impl.SiteMapTrackerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Provides Spring Bean definitions for dependency injection.
 * All wiring for object creation logic is maintained in this file explicitly to keep a track of object hierarchies.
 * Providing wiring logic explicitly will help to debug cyclic dependency issues quickly as the application grows.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
@Configuration
public class AppConfig {

    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool();
    }

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
    public SiteMapTracker<SiteMapEntry> siteMapTracker() {
        Set<SiteMapEntry> allSiteMapEntrySet = new LinkedHashSet<>();

        /**
         * One Set per tag type is created for faster search operation and for providing metrics/reporting
         * specific items search details like total count
         */
        Set<SiteMapEntry> anchorSiteMapEntrySet = new HashSet<>();
        Set<SiteMapEntry> srcSiteMapEntrySet = new HashSet<>();
        Set<SiteMapEntry> linkSiteMapEntrySet = new HashSet<>();

        Map<HtmlTagType, Set<SiteMapEntry>> siteMapEntrySetLookup = new HashMap<>();
        siteMapEntrySetLookup.put(HtmlTagType.ANCHOR, anchorSiteMapEntrySet);
        siteMapEntrySetLookup.put(HtmlTagType.SRC, srcSiteMapEntrySet);
        siteMapEntrySetLookup.put(HtmlTagType.LINK, linkSiteMapEntrySet);
        siteMapEntrySetLookup.put(HtmlTagType.ALL, allSiteMapEntrySet);
        return new SiteMapTrackerImpl(siteMapEntrySetLookup);
    }

    @Bean
    public CrawlTracker<String> crawlTracker() {
        Set<String> visitedUrlSet = Collections.synchronizedSet(new LinkedHashSet<>());
        return new CrawlTrackerImpl(visitedUrlSet);
    }

    @Bean
    @Autowired
    public CrawlService<String> crawlService(CrawlTracker<String> crawlTracker,
                    SiteMapTracker<SiteMapEntry> siteMapTracker,
                    CssQueryProperties cssQueryProperties, AppProperties appProperties,
                    ForkJoinPool pool) {
        Map<HtmlTagType, Map<String, String>> cssQueryLookup = new LinkedHashMap<>();
        cssQueryLookup.put(HtmlTagType.LINK, cssQueryProperties.getLinkQueries());
        cssQueryLookup.put(HtmlTagType.SRC, cssQueryProperties.getSrcQueries());
        cssQueryLookup.put(HtmlTagType.ANCHOR, cssQueryProperties.getAnchorQueries());
        return new CrawlServiceImpl(crawlTracker, siteMapTracker, cssQueryLookup, appProperties, pool);
    }

}
