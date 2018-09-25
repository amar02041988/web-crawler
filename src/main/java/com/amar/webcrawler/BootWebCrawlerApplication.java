package com.amar.webcrawler;

import com.amar.webcrawler.model.bo.SiteMapResult;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootWebCrawlerApplication implements CommandLineRunner {

    @Autowired
    private CrawlService<String> crawlService;

    @Autowired
    private AppProperties appProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootWebCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootWebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length == 0) {
            LOGGER.error("MANDATORY - Missing string argument: URL");
            return;
        } else if (args.length == 1) {
            LOGGER.warn("OPTIONAL - Missing integer argument: DEPTH to limit the crawl hierarchy. It will now crawl complete website");
        }

        String url = args[0];
        appProperties.setDomain(UrlUtils.getDomain(url));

        if (args.length == 2) {
            appProperties.setMaxDepth(Integer.parseInt(args[1]));
        }

        LOGGER.info("=============================================================================");
        LOGGER.info("Crawl configuration - Starting URL: {}, Max depth: {}", url,
                        appProperties.getMaxDepth());
        LOGGER.info("=============================================================================");

        SiteMapResult siteMapResult = crawlService.crawl(url);

        LOGGER.info("=============================================================================");
        LOGGER.info("SiteMap Metrics for CRAWL DEPTH: {}", appProperties.getMaxDepth());
        LOGGER.info("=============================================================================");
        LOGGER.info("Total URL entries in site map: {}", siteMapResult.getAllSiteMapEntries().size());
        LOGGER.info("Total anchor tag's href attribute URL entries in site map: {}", siteMapResult.getAnchorSiteMapEntries().size());
        LOGGER.info("Total src attribute URL entries in site map: {}", siteMapResult.getSrcSiteMapEntries().size());
        LOGGER.info("Total link tag's href attribute URL entries in site map: {}", siteMapResult.getLinkSiteMapEntries().size());
        LOGGER.info("=============================================================================");
    }

    public CrawlService<String> getCrawlService() {
        return crawlService;
    }
    
    public void setCrawlService(CrawlService<String> crawlService) {
        this.crawlService = crawlService;
    }
    
    public AppProperties getAppProperties() {
        return appProperties;
    }

    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

}
