package com.qutopia.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 评论
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Data
@Document(collection = CollectionNames.COMMENT)
public class CommentDO {

    @Id
    private String id;

    /**
     * 被回复的评论id，即父评论id"
     */
    private String originalCommentId;

    /**
     * 被回复评论的评论人名称"
     */
    private String originalCommenterName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论的是文章还是页面
     */
    private String sourceType;

    /**
     * 评论的文章/页面的 id
     */
    private String sourceId;

    /**
     * 评论人ID, 有登陆评论才有
     */
    private String commenterId;

    /**
     * 评论人名称
     */
    private String commenterName;

    /**
     * 评论人的头像图片地址
     */
    private String commenterFigure;

    /**
     * 评论人邮箱
     */
    private String commenterEmail;

    /**
     * 评论时间
     */
    private LocalDateTime commentTime;

}
