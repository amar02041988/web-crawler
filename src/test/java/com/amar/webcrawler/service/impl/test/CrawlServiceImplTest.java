package com.amar.webcrawler.service.impl.test;

import com.amar.webcrawler.service.impl.AppConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CrawlServiceImplTest {

    @Test
    public void shouldRetreiveOnlyLinks() {

        String html = "<html><body>" + "<p>Content</p>"
                        + "<div><a href='http://www.amar.com'>amar</a>"
                        + "<a href='http://www.abc.com'>abc</a>" + "<a href='/xyz'>xyz</a>"
                        + "<link rel='shortcut icon' type='image/x-icon' href='/media/987654.ico' />"
                        + "</div>"
                        + "<div id='imageDiv' class='header'><img name='go' src='go.png' />"
                        + "<img name='hello' src='hello.jpg' />" + "</div>" + "</body></html>";

        Document document = Jsoup.parse(html);
        document.setBaseUri("http://www.amar.com");

        Elements hrefs = document.select(AppConstants.SELECT_URL_KEY);
        System.out.println("hrefs:" + hrefs.size());
        for (Element href : hrefs) {
            System.out.println(href.attr(AppConstants.ABSOLUTE_URL_KEY));
        }

        System.out.println();

        Elements medias = document.select("[src]");
        System.out.println("Media src:" + medias.size());
        for (Element src : medias) {
            System.out.println(src.attr("abs:src"));
        }

        System.out.println();

        Elements importLinks = document.select("link[href]");
        System.out.println("Import Links:" + importLinks.size());
        for (Element link : importLinks) {
            System.out.println(link.attr("abs:href"));
        }
    }
}

