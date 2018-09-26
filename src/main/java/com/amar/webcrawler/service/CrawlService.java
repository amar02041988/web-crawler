package com.amar.webcrawler.service;

import com.amar.webcrawler.model.bo.SiteMapResult;

/**
 * Business service specification to be used for writing implementation for crawling.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public interface CrawlService<T> {

    /**
     * 
     * @param t starting url to crawl 
     * @return {@link SimpleMapResult} to be used for getting site map entries and their metrics.
     */
    public SiteMapResult crawl(T t);
}
