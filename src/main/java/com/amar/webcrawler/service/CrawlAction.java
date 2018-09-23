package com.amar.webcrawler.service;

import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlImpl;
import com.amar.webcrawler.model.constants.AppConstants;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.util.UrlUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public final class CrawlAction extends RecursiveAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2505903207646772261L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlAction.class);

    private final CrawlTracker<SiteMapUrl> crawlTracker;
    private final Map<HtmlTagType, Map<String, String>> cssQueryLookup;
    private final SiteMapUrl siteMapUrlEntry;
    private final AppProperties appProperties;

    public CrawlAction(CrawlTracker<SiteMapUrl> crawlTracker,
                    Map<HtmlTagType, Map<String, String>> cssQueryLookup,
                    SiteMapUrl siteMapUrlEntry, AppProperties appProperties) {
        super();
        this.crawlTracker = crawlTracker;
        this.cssQueryLookup = cssQueryLookup;
        this.siteMapUrlEntry = siteMapUrlEntry;
        this.appProperties = appProperties;
    }

    @Override
    protected void compute() {

        if (!crawlTracker.add(siteMapUrlEntry)) {
            return;
        }

        Document document = UrlUtils.getDocument(siteMapUrlEntry.getLocation(),
                        appProperties.getConnectAndReadTimeoutInMillis());

        if (document != null) {
            cssQueryLookup.forEach((htmlTagType, cssQueries) -> {
                Elements urlElements = document.select(cssQueries.get(AppConstants.SELECT_URL_KEY));

                List<CrawlAction> childCrawlActions = new ArrayList<>();
                for (Element urlElement : urlElements) {
                    String absUrlElement =
                                    urlElement.attr(cssQueries.get(AppConstants.ABSOLUTE_URL_KEY));
                    absUrlElement = UrlUtils.removeFragmentIdentifierIfExist(absUrlElement);

                    if (StringUtils.isEmpty(absUrlElement)) {
                        continue;
                    }

                    SiteMapUrl childSiteMapUrl = new SiteMapUrlImpl(htmlTagType, absUrlElement);

                    if (htmlTagType.equals(HtmlTagType.ANCHOR) && UrlUtils
                                    .isInternalDomain(childSiteMapUrl, appProperties.getDomain())) {
                        childCrawlActions.add(new CrawlAction(crawlTracker, cssQueryLookup,
                                        childSiteMapUrl, appProperties));
                    } else {
                        crawlTracker.add(childSiteMapUrl);
                    }

                }

                invokeAll(childCrawlActions);
            });
        }
    }

    public CrawlTracker<SiteMapUrl> getCrawlTracker() {
        return crawlTracker;
    }


    public Map<HtmlTagType, Map<String, String>> getCssQueryLookup() {
        return cssQueryLookup;
    }


    public SiteMapUrl getSiteMapUrl() {
        return siteMapUrlEntry;
    }


    public AppProperties getAppProperties() {
        return appProperties;
    }

}
