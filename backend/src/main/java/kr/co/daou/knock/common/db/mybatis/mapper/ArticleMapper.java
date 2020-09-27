package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.ArticleLike;
import kr.co.daou.knock.common.db.mybatis.dto.CommentLike;

import java.util.List;

public interface ArticleMapper {

    Article findByIdx(Long idx);

    void updateArticle(Article article);

    void insertArticle(Article article);

    void updateDelYn(Long idx);

    List<Article> findAllByDto(Article article);   // 게시글 목록 조회

    int countByDto(Article article);   // 게시글 수 조회

    void insertArticleLike(ArticleLike articleLike);

    void deleteArticleLike(ArticleLike articleLike);

    int countByUserIdxAndArticleIdx(ArticleLike articleLike);


}
