package com.amar.webcrawler;

import com.amar.webcrawler.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class BootWebCrawlerApplication implements CommandLineRunner {

    @Autowired
    private CrawlService<String> crawlService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String startUrlToCrawl = args[0];
        if (StringUtils.isEmpty(startUrlToCrawl)) {
            LOGGER.error("Please provide URL as command line argument to start crawling");
            return;
        }

        crawlService.crawl(startUrlToCrawl);
    }

    public CrawlService<String> getCrawlService() {
        return crawlService;
    }

    public void setCrawlService(CrawlService<String> crawlService) {
        this.crawlService = crawlService;
    }

}
