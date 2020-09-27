package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Hashtag;

public interface HashtagMapper {

    Hashtag findByHashtag(Hashtag hashtag);

    int countByTag(Hashtag hashtag);

    void insertHashtag(Hashtag hashtag);

}
