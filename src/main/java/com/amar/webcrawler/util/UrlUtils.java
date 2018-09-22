package com.amar.webcrawler.util;

import com.google.common.net.InternetDomainName;

import java.net.URL;

public final class UrlUtils {

    public static void getDomain(String urlString) throws Exception {
        System.out.println(InternetDomainName.from(new URL(urlString).getHost()).topPrivateDomain()
                        .toString());
    }
}
