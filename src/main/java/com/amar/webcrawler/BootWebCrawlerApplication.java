package com.amar.webcrawler;

import com.amar.webcrawler.model.UrlType;
import com.amar.webcrawler.model.bo.SiteMapUrlEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlEntryImpl;
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
    private CrawlService<SiteMapUrlEntry> crawlService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Validate.isTrue(args.length == 1,
                        "Usage: supply URL as command line parameter to start crawling");
        crawlService.crawl(new SiteMapUrlEntryImpl(UrlType.PAGE, args[0]));
    }

    public CrawlService<SiteMapUrlEntry> getCrawlService() {
        return crawlService;
    }

    public void setCrawlService(CrawlService<SiteMapUrlEntry> crawlService) {
        this.crawlService = crawlService;
    }

}
