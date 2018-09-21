package com.amar.webcrawler.service.impl;

import com.amar.webcrawler.BootWebCrawlerApplication;
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
public class CrawlServiceImpl implements CrawlService<String> {

    private CrawlTracker<String> crawlTracker;
    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);


    public CrawlServiceImpl() {
        super();
    }

    @Autowired
    public CrawlServiceImpl(CrawlTracker<String> crawlTracker) {
        super();
        this.crawlTracker = crawlTracker;
    }


    @Override
    public void crawl(String url) {

        Document document = null;

        if (!crawlTracker.isCrawled(url)) {

            if (crawlTracker.addCrawled(url)) {
                LOGGER.info(url);
            }

            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                LOGGER.error("Error occured: {}", e.getMessage());
                e.printStackTrace();
                return;
            }

            Elements hrefsInPage = document.select(Constants.HREF_TAGS);

            for (Element href : hrefsInPage) {
                String absUrl = href.attr(Constants.ABSOLUTE_URL_IN_HREF);

                if (StringUtils.isEmpty(absUrl)) {
                    continue;
                }

                crawl(absUrl);
            }
        }
    }

}
