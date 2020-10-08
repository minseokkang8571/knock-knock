package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.ArticleHashtag;

import java.util.List;

public interface ArticleHashtagMapper {

    void insertHashtag(ArticleHashtag articleHashtag);


    List<ArticleHashtag> findAllByArticleIdx(Long articleIdx);

    void deleteByArticleIdx(Article article);
}
