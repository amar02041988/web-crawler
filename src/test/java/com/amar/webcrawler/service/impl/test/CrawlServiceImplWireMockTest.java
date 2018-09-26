package com.amar.webcrawler.service.impl.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.SiteMapResult;
import com.amar.webcrawler.model.constants.AppConstants;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.amar.webcrawler.model.properties.AppProperties;
import com.amar.webcrawler.model.properties.CssQueryProperties;
import com.amar.webcrawler.service.CrawlService;
import com.amar.webcrawler.service.CrawlTracker;
import com.amar.webcrawler.service.SiteMapTracker;
import com.amar.webcrawler.service.impl.CrawlServiceImpl;
import com.amar.webcrawler.service.impl.CrawlTrackerImpl;
import com.amar.webcrawler.service.impl.SiteMapTrackerImpl;
import com.amar.webcrawler.util.UrlUtils;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

/**
 * Test crawler upto depth 3 for the html files maintained in src/test/resources folder
 * which are hosted using 'Wire Mock' mock-server
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
@RunWith(JMockit.class)
public final class CrawlServiceImplWireMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().dynamicPort());

    private static final String HTML_FOLDER_PATH = "src/test/resources/html";
    private static final String UTF8 = "UTF-8";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "text/html; charset=UTF-8";
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int DEPTH = 3;
    private static final int TIMEOUT_IN_MILLIS = 10000;

    private CrawlService<String> crawlService;
    private CrawlTracker<String> crawlTracker;
    private SiteMapTracker<SiteMapEntry> siteMapTracker;

    @Before
    public void setup() throws IOException {
        AppProperties appProperties = new AppProperties(TIMEOUT_IN_MILLIS, HOST, DEPTH);

        Map<HtmlTagType, Set<SiteMapEntry>> siteMapUrlSetLookup = new HashMap<>();
        siteMapUrlSetLookup.put(HtmlTagType.LINK, new HashSet<>());
        siteMapUrlSetLookup.put(HtmlTagType.SRC, new HashSet<>());
        siteMapUrlSetLookup.put(HtmlTagType.ANCHOR, new HashSet<>());
        siteMapUrlSetLookup.put(HtmlTagType.ALL, new HashSet<>());

        crawlTracker = new CrawlTrackerImpl(new HashSet<>());
        siteMapTracker = new SiteMapTrackerImpl(siteMapUrlSetLookup);

        crawlService = new CrawlServiceImpl(crawlTracker, siteMapTracker, getCssQueryLookup(),
                        appProperties, new ForkJoinPool());

        Map<String, String> htmlFileContents = loadFiles(HTML_FOLDER_PATH);
        registerStubs(htmlFileContents);
    }

    private Map<HtmlTagType, Map<String, String>> getCssQueryLookup() {

        Map<String, String> anchorQueries = new HashMap<>();
        anchorQueries.put(AppConstants.SELECT_URL_KEY, "a[href]");
        anchorQueries.put(AppConstants.ABSOLUTE_URL_KEY, "abs:href");

        Map<String, String> srcQueries = new HashMap<>();
        srcQueries.put(AppConstants.SELECT_URL_KEY, "[src]");
        srcQueries.put(AppConstants.ABSOLUTE_URL_KEY, "abs:src");

        Map<String, String> linkQueries = new HashMap<>();
        linkQueries.put(AppConstants.SELECT_URL_KEY, "link[href]");
        linkQueries.put(AppConstants.ABSOLUTE_URL_KEY, "abs:href");

        CssQueryProperties cssQueryProperties = new CssQueryProperties(anchorQueries, srcQueries, linkQueries);

        Map<HtmlTagType, Map<String, String>> cssQueryLookup = new LinkedHashMap<>();
        cssQueryLookup.put(HtmlTagType.LINK, cssQueryProperties.getLinkQueries());
        cssQueryLookup.put(HtmlTagType.SRC, cssQueryProperties.getSrcQueries());
        cssQueryLookup.put(HtmlTagType.ANCHOR, cssQueryProperties.getAnchorQueries());
        return cssQueryLookup;
    }

    @Test
    public void test() throws Exception {
        int port = wireMockRule.port();

        new MockUp<UrlUtils>() {
            @Mock
            public String getDomain(String url) throws Exception {
                return HOST;
            }
        };

        String url = PROTOCOL + "://" + HOST + ":" + port + "/index.html";
        System.out.println(url);
        SiteMapResult result = crawlService.crawl(url);

        // anchor entries
        Set<SiteMapEntry> anchorSiteMapEntries = result.getAnchorSiteMapEntries();
        Assert.assertEquals("Total site map entries containing urls from anchor tag", 2,
                        anchorSiteMapEntries.size());
        
        anchorSiteMapEntries.forEach(entry -> {
            if(entry.getDepth()==1) {
                Assert.assertTrue("parent.html is crawled", entry.getLocation().contains("parent"));                
            }else {
                Assert.assertTrue("child.html is crawled", entry.getLocation().contains("child"));
            }
            Assert.assertEquals("Crawled html tag type is Anchor", HtmlTagType.ANCHOR, entry.getType());

        });

        // src entries
        Set<SiteMapEntry> srcSiteMapEntries = result.getSrcSiteMapEntries();
        SiteMapEntry entry = srcSiteMapEntries.stream().findFirst().get();
        Assert.assertEquals("Total site map entries containing urls from src tag", 1,
                        srcSiteMapEntries.size());
        Assert.assertEquals("Dept index of src site map entry", 2, entry.getDepth());
        Assert.assertEquals("Crawled html tag type is src", HtmlTagType.SRC, entry.getType());

        // link entries
        Set<SiteMapEntry> linkSiteMapEntries = result.getLinkSiteMapEntries();
        entry = linkSiteMapEntries.stream().findFirst().get();
        Assert.assertEquals("Total site map entries containing urls from link tag", 1,
                        linkSiteMapEntries.size());
        Assert.assertEquals("Dept index of link site map entry", 3, entry.getDepth());
        Assert.assertEquals("Crawled html tag type is link", HtmlTagType.LINK, entry.getType());

    }

    private Map<String, String> loadFiles(String path) {
        File resourcesDirectory = new File(path);
        String[] filenames = resourcesDirectory.list();
        Map<String, String> fileContents = new HashMap<>();

        Stream.of(filenames).forEach(filename -> {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(path + "/" + filename);
                String html = new String(IOUtils.toByteArray(inputStream), Charset.forName(UTF8));
                fileContents.put(filename, html);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return fileContents;
    }

    private void registerStubs(Map<String, String> htmlFileContents) {
        htmlFileContents.forEach((fileName, fileContent) -> {
            wireMockRule.stubFor(get(urlEqualTo("/" + fileName)).willReturn(
                            aResponse().withBody(htmlFileContents.get(fileName)).withHeader(
                                            CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE)));
        });
    }
}
