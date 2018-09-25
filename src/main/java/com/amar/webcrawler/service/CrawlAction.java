package com.amar.webcrawler.service;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.model.constants.AppConstants;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.impl.SiteMapTracker;
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

    private static final long serialVersionUID = -2505903207646772261L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlAction.class);

    private final String url;
    private final int depth;
    private final CrawlTracker<String> crawlTracker;
    private final SiteMapTracker<SiteMapEntry> siteMapTracker;
    private final Map<HtmlTagType, Map<String, String>> cssQueryLookup;
    private final AppProperties appProperties;


    public CrawlAction(String url, int depth, CrawlTracker<String> crawlTracker,
                    SiteMapTracker<SiteMapEntry> siteMapTracker,
                    Map<HtmlTagType, Map<String, String>> cssQueryLookup,
                    AppProperties appProperties) {
        super();
        this.url = url;
        this.depth = depth;
        this.crawlTracker = crawlTracker;
        this.siteMapTracker = siteMapTracker;
        this.cssQueryLookup = cssQueryLookup;
        this.appProperties = appProperties;
    }


    @Override
    protected void compute() {
        if (!crawlTracker.isVisited(url)) {
            crawlTracker.addVisited(url);
            Document document = UrlUtils.getDocument(url, appProperties.getConnectAndReadTimeoutInMillis());
            
            if (document != null) {
                cssQueryLookup.forEach((htmlTagType, cssQueries) -> {
                    Elements elements = document.select(cssQueries.get(AppConstants.SELECT_URL_KEY));

                    List<CrawlAction> childCrawlActions = new ArrayList<>();

                    for (Element element : elements) {
                        String childElement = element.attr(cssQueries.get(AppConstants.ABSOLUTE_URL_KEY));
                        childElement = UrlUtils.removeFragmentIdentifierIfExist(childElement);

                        if (depth < appProperties.getMaxDepth()
                                        && !StringUtils.isEmpty(childElement)
                                        && !crawlTracker.isVisited(childElement)
                                        && UrlUtils.isInternalDomain(childElement,
                                                        appProperties.getDomain())) {
                            int newDepth = depth + 1;
                            SiteMapEntry siteMapEntry = new SiteMapEntryImpl(newDepth, htmlTagType, childElement);

                            if (!siteMapTracker.isAlreadyInSiteMap(siteMapEntry)) {
                                LOGGER.info(((SiteMapEntryImpl)siteMapEntry).toStringMinimal());
                                siteMapTracker.addToSiteMap(siteMapEntry);

                                if (htmlTagType.equals(HtmlTagType.ANCHOR)) {
                                    childCrawlActions.add(new CrawlAction(childElement, newDepth, crawlTracker, siteMapTracker, cssQueryLookup, appProperties));
                                }
                            }
                        }
                    }

                    if (childCrawlActions.size() > 0) {
                        invokeAll(childCrawlActions);
                    }
                });
            }
        }
    }

}
