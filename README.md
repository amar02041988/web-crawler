# web-crawler

Spring Boot, Multi-threaded, Fork-Join based Web-crawler implementation.

##Tested using:
* Wire Mock
* Mockito

##Feature highlights

* Searches urls that are part of anchor tag, src attribute and link tag.
* Depth can be specified in order to limit the search at an hierarchy level.
* Multi-threaded approach using the ForkJoin Java 7 feature as every internal domain link is further crawled/followed recursively based on the depth.
* Url's of internal domains are crawled/followed further down the tree.
* External domain Url's like facebook/twitter are only searched and maintained in site map set, but NOT crawled/ NOT followed further down the tree.  
* Every url is maintained in Set.
* There is one global Set which holds all the urls as SiteMapEntry, which can be used further to create SiteMap of different format(XML, HTML)
* SiteMapEntry business model is designed to hold every granular details that might be useful to create final SiteMap 
* There are also 3 separate specific Sets that holds urls specific to the tag/attribute from which it is fetched i.e Sets for anchor urls, src urls and link urls. Maintaining these additional separate Set helps to get the metrics of the items searched.

##To-be done(if more time provided)
* Convert it into an API using Spring Rest
* Adding different type of SiteMap format generation feature like XML, HTML..etc
* Putting more URL based validations for security constraints
* Proper exception handling at generic level

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

####What things you need
* Java v8(https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven v3.x.x](https://maven.apache.org/) - Dependency Management

### Running Build and test:
* Open command prompt and change directory(cd) to a location where you want to clone the project
 
* Clone URL: 
	clone https://github.com/amar02041988/web-crawler.git

* Switch to development branch: 
	git checkout development
	
* Go to project base folder: web-crawler where pom.xml file exist

* Build project:
	mvn clean install
	
* Run project from base folder(web-crawler) by providing 2 command line arguments, https://www.prudential.co.uk/ as 
   starting url to crawl and 1 for crawl depth(You can set this depth to any level, indicating how deep the url and its
   child should be crawled). Providing depth is optional:
   
	java -jar target/web-crawler-0.0.1-SNAPSHOT.jar https://www.prudential.co.uk/ 1

## Author

* **Amar Panigrahy** - (https://github.com/amar02041988)