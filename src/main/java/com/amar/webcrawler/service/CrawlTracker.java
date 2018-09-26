package com.amar.webcrawler.service;

/**
 * Business service specification to be used for writing implementation for crawl tracker.
 * It keeps a track of urls that are visited/new.
 *  
 * @author  Amar Panigrahy
 * @version 1.0
 */
public interface CrawlTracker<T> {

    /**
     * 
     * @param url if it has already been crawled
     * @return true if crawled and false for new url
     */
    public boolean isVisited(T url);

    /**
     * 
     * @param url to be added in crawl tracker so that it's not crawled again.
     * @return true if url gets added in tracker else false
     */
    public boolean addVisited(T url);


}
