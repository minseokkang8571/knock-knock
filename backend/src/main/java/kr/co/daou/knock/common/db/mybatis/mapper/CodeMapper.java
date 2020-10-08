package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Language;

public interface CodeMapper {


    void insertCode(Code code);

    int countByLanguage(Language language);

    void deleteByArticleIdx(Article article);

}
