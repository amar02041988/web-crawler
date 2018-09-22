package com.amar.webcrawler;

import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.service.CrawlService;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootWebCrawlerApplication implements CommandLineRunner {

    @Autowired
    private CrawlService<SiteMapEntryImpl> crawlService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Validate.isTrue(args.length == 1,
                        "Usage: supply URL as command line parameter to start crawling");
        crawlService.crawl(new SiteMapEntryImpl(args[0]));
    }

    public CrawlService<SiteMapEntryImpl> getCrawlService() {
        return crawlService;
    }

    public void setCrawlService(CrawlService<SiteMapEntryImpl> crawlService) {
        this.crawlService = crawlService;
    }

}
