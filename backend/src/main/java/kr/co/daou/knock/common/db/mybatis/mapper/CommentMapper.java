package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Comment;

import java.util.List;

public interface CommentMapper {

    List<Comment> findAllByArticleIdx(Long idx);


}
