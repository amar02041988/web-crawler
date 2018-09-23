package com.amar.webcrawler.util;

import com.amar.webcrawler.model.bo.SiteMapUrl;
import com.amar.webcrawler.model.constants.AppConstants;
import com.amar.webcrawler.model.constants.HtmlTagType;
import com.google.common.net.InternetDomainName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

public final class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    public static String getDomain(String urlString) throws Exception {
        return InternetDomainName.from(new URL(urlString).getHost()).topPrivateDomain().toString()
                        .toLowerCase();
    }

    public static String removeFragmentIdentifierIfExist(String url) {
        int fragmentIdentifierIndex = url.indexOf(AppConstants.FRAGMENT_IDENTIFIER);
        if (fragmentIdentifierIndex > -1) {
            url = url.substring(0, fragmentIdentifierIndex);
        }
        return url;
    }

    public static final boolean isInternalDomain(SiteMapUrl siteMapUrl, String expectedDomain) {
        try {
            if (siteMapUrl.getType().equals(HtmlTagType.ANCHOR)) {
                String domainFound = UrlUtils.getDomain(siteMapUrl.getLocation());
                if (!StringUtils.isEmpty(domainFound) && domainFound.equals(expectedDomain)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Skipping URL: {} due to error while getting domain: {}: ",
                            siteMapUrl.getLocation(), ex.getMessage());
        }
        return false;
    }

    public static final Document getDocument(String url, int timeoutInMillis) {
        try {
            return Jsoup.connect(url).timeout(timeoutInMillis).ignoreHttpErrors(true)
                            .ignoreContentType(true).get();
        } catch (IOException e) {
            LOGGER.error("Error occured while getting document for url: {}, Error: {}", url,
                            e.getMessage());
        }
        return null;
    }

    public static void printSiteMapUrl(SiteMapUrl siteMapUrl) {
        LOGGER.info("Type: {}, URL: {}", siteMapUrl.getType(), siteMapUrl.getLocation());
    }

}
