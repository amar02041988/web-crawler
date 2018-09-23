package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlEntryImpl;
import com.amar.webcrawler.model.constants.UrlType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import com.amar.webcrawler.util.UrlUtils;
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
    }

    @Override
    public void crawl(SiteMapUrlEntry siteMapUrl) {

        if (!(isInternalDomain(siteMapUrl) && crawlTracker.add(siteMapUrl))) {
            return;
        }

        LOGGER.info("Type: {}, URL: {}", siteMapUrl.getType(), siteMapUrl.getLocation());

        Document document = null;
        try {
            document = Jsoup.connect(siteMapUrl.getLocation())
                            .timeout(appProperties.getConnectAndReadTimeoutInMillis())
                            .ignoreHttpErrors(true).ignoreContentType(true).get();
        } catch (IOException e) {
            LOGGER.error("Error occured: {}", e.getMessage());
            e.printStackTrace();
            return;
        }

        extractUrls(document, cssQueryLookup);

    }

    protected void extractUrls(Document document,
                    Map<UrlType, Map<String, String>> cssQueryLookup) {
        cssQueryLookup.forEach((urlType, cssQueries) -> {
            Elements elementsWithUrl = document.select(cssQueries.get(AppConstants.SELECT_URL_KEY));
            for (Element elementWithUrl : elementsWithUrl) {
                String elementWithAbsoluteUrl =
                                elementWithUrl.attr(cssQueries.get(AppConstants.ABSOLUTE_URL_KEY));

                elementWithAbsoluteUrl = removeFragmentIdentifierIfExist(elementWithAbsoluteUrl);

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

    protected boolean isInternalDomain(SiteMapUrlEntry siteMapUrl) {
        try {
            if (siteMapUrl.getType().equals(UrlType.HREF)) {
                String domain = UrlUtils.getDomain(siteMapUrl.getLocation());
                if (!StringUtils.isEmpty(domain) && domain.equals(appProperties.getDomain())) {
                    return true;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Skipping URL: {} due to error while getting domain: {}: ",
                            siteMapUrl.getLocation(), ex.getMessage());
        }
        return false;
    }

    protected String removeFragmentIdentifierIfExist(String url) {
        int fragmentIdentifierIndex = url.indexOf(AppConstants.FRAGMENT_IDENTIFIER);
        if (fragmentIdentifierIndex > 0) {
            url = url.substring(0, fragmentIdentifierIndex);
        }
        return url;
    }

}
