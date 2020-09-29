package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.Comment;
import kr.co.daou.knock.common.db.mybatis.dto.CommentLike;

import java.util.List;

public interface CommentMapper {

    List<Comment> findAllByArticleIdx(Article article);

    int countByDto(Article article);   // 답글 수 조회

    int maxGroupOrd(Comment comment);

    void updateComment(Comment comment);

    void insertComment(Comment comment);

    void updateOriginIdx(Comment comment);

    void updateDelYn(Long idx);

    void insertCommentLike(CommentLike commentLike);

    void deleteCommentLike(CommentLike commentLike);

    int countByUserIdxAndCommentIdx(CommentLike commentLike);

}
