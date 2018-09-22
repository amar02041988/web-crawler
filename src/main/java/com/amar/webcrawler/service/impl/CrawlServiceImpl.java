package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.CssQueries;
import com.amar.webcrawler.model.UrlType;
import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlEntryImpl;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class CrawlServiceImpl implements CrawlService<SiteMapUrlEntry> {

    private final CrawlTracker<SiteMapUrlEntry> crawlTracker;
    private final Map<UrlType, CssQueries> cssQueriesLookup;
    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public CrawlServiceImpl(CrawlTracker<SiteMapUrlEntry> crawlTracker,
                    Map<UrlType, CssQueries> cssQueriesLookup) {
        super();
        this.crawlTracker = crawlTracker;
        this.cssQueriesLookup = cssQueriesLookup;
    }

    @Override
    public void crawl(SiteMapUrlEntry siteMapUrl) {

        Document document = null;

        if (!crawlTracker.isCrawled(siteMapUrl)) {

            if (crawlTracker.addCrawled(siteMapUrl)) {
                LOGGER.info(siteMapUrl.toString());
            }

            if (!siteMapUrl.getType().equals(UrlType.PAGE)) {
                return;
            }

            try {
                document = Jsoup.connect(siteMapUrl.getLocation()).get();
            } catch (IOException e) {
                LOGGER.error("Error occured: {}", e.getMessage());
                e.printStackTrace();
                return;
            }

            extractUrls(document, cssQueriesLookup);

        }
    }

    protected void extractUrls(Document document, Map<UrlType, CssQueries> cssQueriesLookup) {
        cssQueriesLookup.forEach((urlType, cssQueries) -> {
            Elements elementsWithUrl = document.select(cssQueries.getSelectUrl());
            for (Element elementWithUrl : elementsWithUrl) {
                String elementWithAbsoluteUrl = elementWithUrl.attr(cssQueries.getAbsoluteUrl());
                if (StringUtils.isEmpty(elementWithAbsoluteUrl)) {
                    continue;
                }
                SiteMapUrlEntry siteMapUrlEntry =
                                new SiteMapUrlEntryImpl(urlType, elementWithAbsoluteUrl);
                crawl(siteMapUrlEntry);
            }
        });
    }

    public CrawlTracker<SiteMapUrlEntry> getCrawlTracker() {
        return crawlTracker;
    }

    public Map<UrlType, CssQueries> getCssQueriesLookup() {
        return cssQueriesLookup;
    }

}
