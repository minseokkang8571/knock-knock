package kr.co.daou.knock.article.service;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.ArticleLike;
import kr.co.daou.knock.common.db.mybatis.dto.Comment;
import kr.co.daou.knock.common.db.mybatis.dto.CommentLike;

public interface ArticleService {
	String list(Article article);
	String view(Article article);
	String save(Article article);
	String delete(Article article);
	String likeSave(ArticleLike articleLike);
	String likeDelete(ArticleLike articleLike);
	String commentCall(Article article);
	String commentSave(Comment comment);
	String commentDelete(Comment comment);
	String commentLikeSave(CommentLike commentLike);
	String commentLikeDelete(CommentLike commentLike);

}
