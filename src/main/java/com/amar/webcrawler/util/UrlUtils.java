package com.amar.webcrawler.util;

import com.google.common.net.InternetDomainName;

import java.net.URL;

public final class UrlUtils {

    public static String getDomain(String urlString) throws Exception {
        return InternetDomainName.from(new URL(urlString).getHost()).topPrivateDomain().toString()
                        .toLowerCase();
    }
}
