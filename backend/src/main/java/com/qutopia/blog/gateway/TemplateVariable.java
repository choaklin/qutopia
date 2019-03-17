package com.qutopia.blog.gateway;


/**
 * 模板变量
 *
 * @author polyfish
 * @date 2018.12.1
 */
public interface TemplateVariable {

    String PAGE_NUMBER = "page_number";
    String PAGE_SIZE = "page_size";
    String TOTAL_PAGES = "total_pages";

    ///////////////////////////////////////// 首页的模板变量 ////////////////////////////////
    /**
     * 最近旅行的快照链接
     */
    String LATEST_TRAVEL_THUMBNAILS = "latestTravelThumbnails";



    ///////////////////////////////////////// 文章列表页的模板变量 ////////////////////////////////
    String ARTICLES = "articles";

    String ARTICLE_LINK = "article_link";
    String ARTICLE = "article";
    String PREV_ARTICLE = "prev_article";
    String NEXT_ARTICLE = "next_article";


    String CATEGORIES = "categories";
    String TAGS = "tags";
}
