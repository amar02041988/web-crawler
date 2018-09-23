package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlEntryImpl;
import com.amar.webcrawler.model.constants.UrlType;
import com.amar.webcrawler.model.properties.AppProperties;
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
    private final Map<UrlType, Map<String, String>> cssQueryLookup;
    private AppProperties appProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public CrawlServiceImpl(CrawlTracker<SiteMapUrlEntry> crawlTracker,
                    Map<UrlType, Map<String, String>> cssQueryLookup, AppProperties appProperties) {
        super();
        this.crawlTracker = crawlTracker;
        this.cssQueryLookup = cssQueryLookup;
        this.appProperties = appProperties;

        System.out.println(appProperties);
        System.out.println(cssQueryLookup.get(UrlType.PAGE));
        System.out.println(cssQueryLookup.get(UrlType.MEDIA));
        System.out.println(cssQueryLookup.get(UrlType.IMPORT_LINK));
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
                document = Jsoup.connect(siteMapUrl.getLocation())
                                .timeout(appProperties.getConnectAndReadTimeoutInMillis()).get();
            } catch (IOException e) {
                LOGGER.error("Error occured: {}", e.getMessage());
                e.printStackTrace();
                return;
            }

            extractUrls(document, cssQueryLookup);

        }
    }

    protected void extractUrls(Document document,
                    Map<UrlType, Map<String, String>> cssQueryLookup) {
        cssQueryLookup.forEach((urlType, cssQueries) -> {
            System.out.println("Crawling URL of type: " + urlType);
            Elements elementsWithUrl = document.select(cssQueries.get(AppConstants.SELECT_URL_KEY));
            for (Element elementWithUrl : elementsWithUrl) {
                String elementWithAbsoluteUrl =
                                elementWithUrl.attr(cssQueries.get(AppConstants.ABSOLUTE_URL_KEY));
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


    public Map<UrlType, Map<String, String>> getCssQueryLookup() {
        return cssQueryLookup;
    }


    public AppProperties getAppProperties() {
        return appProperties;
    }


    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

}
