package com.amar.webcrawler;

import com.amar.webcrawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootWebCrawlerApplication implements CommandLineRunner {

    @Autowired
    private CrawlerService crawlService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        crawlService.crawl();
    }

    public CrawlerService getCrawlService() {
        return crawlService;
    }

    public void setCrawlService(CrawlerService crawlService) {
        this.crawlService = crawlService;
    }

}
