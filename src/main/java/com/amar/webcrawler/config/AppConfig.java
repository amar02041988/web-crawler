package com.amar.webcrawler.config;

import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.constants.HtmlTagType;
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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class AppConfig {

    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool();
    }

    @Bean
    public ReentrantLock crawlTrackLock() {
        return new ReentrantLock();
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
    @Autowired
    public CrawlTracker<SiteMapUrl> crawlTracker(AppProperties appProperties,
                    ReentrantLock reentrantLock) {
        Set<SiteMapUrl> allSiteMapUrlSet = new LinkedHashSet<>();
        Set<SiteMapUrl> anchorSiteMapUrlSet = new HashSet<>();
        Set<SiteMapUrl> srcSiteMapUrlSet = new HashSet<>();
        Set<SiteMapUrl> linkSiteMapUrlSet = new HashSet<>();

        Map<HtmlTagType, Set<SiteMapUrl>> siteMapUrlSetLookup = new HashMap<>();
        siteMapUrlSetLookup.put(HtmlTagType.ANCHOR, anchorSiteMapUrlSet);
        siteMapUrlSetLookup.put(HtmlTagType.SRC, srcSiteMapUrlSet);
        siteMapUrlSetLookup.put(HtmlTagType.LINK, linkSiteMapUrlSet);
        siteMapUrlSetLookup.put(HtmlTagType.ALL, allSiteMapUrlSet);
        return new CrawlTrackerImpl(siteMapUrlSetLookup, appProperties, reentrantLock);
    }

    @Bean
    @Autowired
    public CrawlService<SiteMapUrl> crawlService(CrawlTracker<SiteMapUrl> crawlTracker,
                    CssQueryProperties cssQueryProperties, AppProperties appProperties,
                    ForkJoinPool pool) {
        Map<HtmlTagType, Map<String, String>> cssQueryLookup = new LinkedHashMap<>();
        cssQueryLookup.put(HtmlTagType.LINK, cssQueryProperties.getLinkQueries());
        cssQueryLookup.put(HtmlTagType.SRC, cssQueryProperties.getSrcQueries());
        cssQueryLookup.put(HtmlTagType.ANCHOR, cssQueryProperties.getAnchorQueries());
        return new CrawlServiceImpl(crawlTracker, cssQueryLookup, appProperties, pool);
    }

}
