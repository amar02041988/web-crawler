package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class CrawlServiceImpl implements CrawlService<SiteMapEntryImpl> {

    private final CrawlTracker<SiteMapEntryImpl> crawlTracker;
    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);


    @Autowired
    public CrawlServiceImpl(CrawlTracker<SiteMapEntryImpl> crawlTracker) {
        super();
        this.crawlTracker = crawlTracker;
    }


    @Override
    public void crawl(SiteMapEntryImpl siteMapUrl) {

        Document document = null;

        if (!crawlTracker.isCrawled(siteMapUrl)) {

            if (crawlTracker.addCrawled(siteMapUrl)) {
                LOGGER.info(siteMapUrl.toString());
            }

            try {
                document = Jsoup.connect(siteMapUrl.getLocation()).get();
            } catch (IOException e) {
                LOGGER.error("Error occured: {}", e.getMessage());
                e.printStackTrace();
                return;
            }

            Elements hrefsInPage = document.select(Constants.PAGE_URL_IN_HREF);

            for (Element href : hrefsInPage) {
                String absUrl = href.attr(Constants.ABSOLUTE_PAGE_URL_IN_HREF);

                if (StringUtils.isEmpty(absUrl)) {
                    continue;
                }

                crawl(new SiteMapEntryImpl(absUrl));
            }
        }
    }

}
