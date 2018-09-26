package com.amar.webcrawler.util;

import com.amar.webcrawler.model.bo.SiteMapEntry;
import com.amar.webcrawler.model.bo.impl.SiteMapEntryImpl;
import com.amar.webcrawler.model.constants.AppConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Provides out of the box utility methods for Urls
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public final class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    /**
     * Get the domain/host from url
     * @param url to find it's domain
     * @return {@link String} representing domain
     * @throws Exception
     */
    public static String getDomain(String url) throws Exception {
        return new URL(url).getHost().toLowerCase();
    }


    /**
     * Removes the fragment identifier(#) from url
     * @param url to remove the fragment identifier(#) if present
     * @return url without fragment identifier(#)
     */
    public static String removeFragmentIdentifierIfExist(String url) {
        int fragmentIdentifierIndex = url.indexOf(AppConstants.FRAGMENT_IDENTIFIER);
        if (fragmentIdentifierIndex > -1) {
            url = url.substring(0, fragmentIdentifierIndex);
        }
        return url;
    }

    /**
     * Matches domain of the provided url against the provided domain(domain of starting url).
     * @param url get it's domain
     * @param expectedDomain domain of starting url.
     * @return true if domain is same else false
     */
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

    /**
     * Get parsed html document. 
     * @param url whose document object need to be returned
     * @param timeoutInMillis timeout if jsoup is not able to connect/read the http resource 
     * @return {@link Document}
     */
    public static final Document getDocument(String url, int timeoutInMillis) {
        try {
            return Jsoup.connect(url).timeout(timeoutInMillis).ignoreHttpErrors(true).ignoreContentType(true).get();
        } catch (IOException e) {
            LOGGER.error("Error occured while getting document for url: {}, Error: {}", url,
                            e.getMessage());
        }
        return null;
    }

    /**
     * Print {@link SiteMapEntry} minimal details
     * @param siteMapEntry to be printed
     */
    public static void printSiteMapUrl(SiteMapEntry siteMapEntry) {
        LOGGER.info(((SiteMapEntryImpl) siteMapEntry).toStringMinimal());
    }
}
