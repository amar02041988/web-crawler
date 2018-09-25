package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.SiteMapResult;
import com.amar.webcrawler.model.bo.impl.SiteMapResultImpl;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlAction;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class CrawlServiceImpl implements CrawlService<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    private final CrawlTracker<String> crawlTracker;
    private final SiteMapTracker<SiteMapEntry> siteMapTracker;
    private final Map<HtmlTagType, Map<String, String>> cssQueryLookup;
    private AppProperties appProperties;
    private ForkJoinPool pool;

    public CrawlServiceImpl(CrawlTracker<String> crawlTracker,
                    SiteMapTracker<SiteMapEntry> siteMapTracker,
                    Map<HtmlTagType, Map<String, String>> cssQueryLookup,
                    AppProperties appProperties, ForkJoinPool forkJoinPool) {
        super();
        this.crawlTracker = crawlTracker;
        this.siteMapTracker = siteMapTracker;
        this.cssQueryLookup = cssQueryLookup;
        this.appProperties = appProperties;
        this.pool = forkJoinPool;
    }

    @Override
    public SiteMapResult crawl(String url) {
        try {
            pool.invoke(new CrawlAction(url, 0, crawlTracker, siteMapTracker, cssQueryLookup,
                            appProperties));
        } finally {
            pool.shutdown();
        }

        Set<SiteMapEntry> allSiteMapEntries =
                        siteMapTracker.getSiteMapUrlSetLookup().get(HtmlTagType.ALL);
        Set<SiteMapEntry> anchorSiteMapEntries =
                        siteMapTracker.getSiteMapUrlSetLookup().get(HtmlTagType.ANCHOR);
        Set<SiteMapEntry> srcSiteMapEntries =
                        siteMapTracker.getSiteMapUrlSetLookup().get(HtmlTagType.SRC);
        Set<SiteMapEntry> linkSiteMapEntries =
                        siteMapTracker.getSiteMapUrlSetLookup().get(HtmlTagType.LINK);

        return new SiteMapResultImpl(allSiteMapEntries, anchorSiteMapEntries, srcSiteMapEntries,
                        linkSiteMapEntries);
    }

    public CrawlTracker<String> getCrawlTracker() {
        return crawlTracker;
    }

    public SiteMapTracker<SiteMapEntry> getSiteMapTracker() {
        return siteMapTracker;
    }

    public void setPool(ForkJoinPool pool) {
        this.pool = pool;
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
