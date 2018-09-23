package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlAction;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class CrawlServiceImpl implements CrawlService<SiteMapUrl> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    private final CrawlTracker<SiteMapUrl> crawlTracker;
    private final Map<HtmlTagType, Map<String, String>> cssQueryLookup;
    private AppProperties appProperties;
    private ForkJoinPool pool;

    public CrawlServiceImpl(CrawlTracker<SiteMapUrl> crawlTracker,
                    Map<HtmlTagType, Map<String, String>> cssQueryLookup,
                    AppProperties appProperties, ForkJoinPool forkJoinPool) {
        super();
        this.crawlTracker = crawlTracker;
        this.cssQueryLookup = cssQueryLookup;
        this.appProperties = appProperties;
        this.pool = forkJoinPool;
    }

    @Override
    public void crawl(SiteMapUrl siteMapUrl) {
        try {
            pool.invoke(new CrawlAction(crawlTracker, cssQueryLookup, siteMapUrl, appProperties));
        } finally {
            pool.shutdown();
        }
    }

    public CrawlTracker<SiteMapUrl> getCrawlTracker() {
        return crawlTracker;
    }


    public Map<HtmlTagType, Map<String, String>> getCssQueryLookup() {
        return cssQueryLookup;
    }


    public AppProperties getAppProperties() {
        return appProperties;
    }


    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public ForkJoinPool getPool() {
        return pool;
    }

}
