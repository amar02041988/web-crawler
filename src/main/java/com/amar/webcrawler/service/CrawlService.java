package com.amar.webcrawler.service;

import com.amar.webcrawler.model.bo.SiteMapResult;

public interface CrawlService<T> {

    public SiteMapResult crawl(T t);
}
