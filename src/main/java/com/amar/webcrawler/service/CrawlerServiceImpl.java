package com.amar.webcrawler.service;

import com.amar.webcrawler.BootWebCrawlerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    @Override
    public void crawl() {
        System.out.println("crawl");
    }


}
