package com.amar.webcrawler;

import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.bo.impl.SiteMapUrlImpl;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.util.UrlUtils;
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
    private CrawlService<SiteMapUrl> crawlService;

    @Autowired
    private AppProperties appProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Validate.isTrue(args.length == 1,
                        "Usage: supply URL as command line parameter to start crawling");
        String url = args[0];
        appProperties.setDomain(UrlUtils.getDomain(url));
        crawlService.crawl(new SiteMapUrlImpl(HtmlTagType.ANCHOR, url));
    }

    public CrawlService<SiteMapUrl> getCrawlService() {
        return crawlService;
    }

    public void setCrawlService(CrawlService<SiteMapUrl> crawlService) {
        this.crawlService = crawlService;
    }


    public AppProperties getAppProperties() {
        return appProperties;
    }


    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

}
