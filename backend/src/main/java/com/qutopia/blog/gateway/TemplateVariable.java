package com.qutopia.blog.gateway;


/**
 * 模板变量
 *
 * @author polyfish
 * @date 2018.12.1
 */
public interface TemplateVariable {

    String PAGE_NO = "pageNo";
    String PAGE_SIZE = "pageSize";

    ///////////////////////////////////////// 首页的模板变量 ////////////////////////////////
    /**
     * 最近旅行的快照链接
     */
    String LATEST_TRAVEL_THUMBNAILS = "latestTravelThumbnails";



    ///////////////////////////////////////// 文章列表页的模板变量 ////////////////////////////////
    String ARTICLES = "articles";
    String ARTICLE = "article";


    String CATEGORIES = "categories";
    String TAGS = "tags";
}
