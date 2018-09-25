package com.amar.webcrawler.util;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.model.constants.AppConstants;
import com.amar.webcrawler.model.properties.CssQueryProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public final class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    public static String getDomain(String url) throws Exception {
        return new URL(url).getHost().toLowerCase();
    }

    public static String removeFragmentIdentifierIfExist(String url) {
        int fragmentIdentifierIndex = url.indexOf(AppConstants.FRAGMENT_IDENTIFIER);
        if (fragmentIdentifierIndex > -1) {
            url = url.substring(0, fragmentIdentifierIndex);
        }
        return url;
    }

    public static final boolean isInternalDomain(String url, String expectedDomain) {
        try {
            String domainFound = UrlUtils.getDomain(url);
            if (!StringUtils.isEmpty(domainFound) && domainFound.equals(expectedDomain)) {
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error("Skipping URL: {} due to error while getting domain: {}: ", url,
                            ex.getMessage());
        }
        return false;
    }

    public static final Document getDocument(String url, int timeoutInMillis) {
        try {
            return Jsoup.connect(url).timeout(timeoutInMillis).ignoreHttpErrors(true).ignoreContentType(true).get();
        } catch (IOException e) {
            LOGGER.error("Error occured while getting document for url: {}, Error: {}", url,
                            e.getMessage());
        }
        return null;
    }

    public static void printSiteMapUrl(SiteMapEntry siteMapUrl) {
        LOGGER.info(((SiteMapEntryImpl) siteMapUrl).toStringMinimal());
    }
}
