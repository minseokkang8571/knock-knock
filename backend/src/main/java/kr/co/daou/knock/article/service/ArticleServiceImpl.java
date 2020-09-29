package kr.co.daou.knock.article.service;

import kr.co.daou.knock.common.db.mybatis.dto.*;
import kr.co.daou.knock.common.db.mybatis.mapper.ArticleHashtagMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.ArticleMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.CommentMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.HashtagMapper;
import kr.co.daou.knock.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends CommonService implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleHashtagMapper articleHashtagMapper;

    @Autowired
    private HashtagMapper hashtagMapper;

    /**
     * FUNCTION :: 게시글 리스트
     * @param article
     * @return
     */
    public String list(Article article) {
        Map<String, Object> rtnMap = returnMap();
        try{
            int totalCount = articleMapper.countByDto(article);
            setDefaultPaging(rtnMap, article, totalCount);
            List<Article> articleList = articleMapper.findAllByDto(article);
            for(int i = 0;i<articleList.size();i++){
                articleList.get(i).setArticleHashtagList(articleHashtagMapper.findAllByArticleIdx(articleList.get(i).getIdx()));
            }
            rtnMap.put("articleList", articleList);
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 게시글 상세
     * @param article
     * @return
     */
    public String view(Article article) {
        Map<String, Object> rtnMap = returnMap();
        try{
            article = articleMapper.findByIdx(article.getIdx());

            int totalCount = commentMapper.countByDto(article);
            setDefaultPaging(rtnMap, article, totalCount);
            List<Comment> comment = commentMapper.findAllByArticleIdx(article);

            rtnMap.put("article", article);
            rtnMap.put("comment", comment);
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 답글 호출
     * @param article
     * @return
     */
    public String commentCall(Article article) {
        Map<String, Object> rtnMap = returnMap();
        try{
            int totalCount = commentMapper.countByDto(article);
            setDefaultPaging(rtnMap, article, totalCount);
            List<Comment> comment = commentMapper.findAllByArticleIdx(article);
            rtnMap.put("comment", comment);
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 게시글 등록
     * @param article
     * @return
     */
    @Transactional
    public String save(Article article) {
        Map<String, Object> rtnMap = returnMap();
        try{
            if(article.getIdx() != null){   // 수정일 경우
                articleMapper.updateArticle(article);
            } else {    // 등록일 경우
                articleMapper.insertArticle(article);
                article.setIdx(article.getIdx());
                //TODO :: 코드테이블에 데이터 등록해야함
                //해시태그 추출
                Hashtag hashtag = new Hashtag();
                hashtag.setTag(article.getContents().substring(article.getContents().indexOf("```")+1).split(" ")[0]);
                //추출된 해시태그 hashtag 테이블에 있는지 확인
                if(hashtagMapper.countByTag(hashtag) == 0){
                    //해시태그가 없으면 tag 테이블에 추출된 해시태그 삽입
                    hashtagMapper.insertHashtag(hashtag);
                } else{
                    hashtag = hashtagMapper.findByHashtag(hashtag);
                }
                //articleHashtag 테이블에 추출된 해시태그 정보 삽입
                ArticleHashtag articleHashtag = new ArticleHashtag();
                articleHashtag.setArticleIdx(article.getIdx());
                articleHashtag.setHashtagIdx(hashtag.getIdx());
                articleHashtagMapper.insertHashtag(articleHashtag);
            }
            rtnMap.put("idx", article.getIdx());
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 게시글 삭제
     * @param article
     * @return
     */
    @Transactional
    public String delete(Article article) {
        Map<String, Object> rtnMap = returnMap();
        try {
            articleMapper.updateDelYn(article.getIdx());
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 게시글 좋아요 등록
     * @param articleLike
     * @return
     */
    @Transactional
    public String likeSave(ArticleLike articleLike) {
        Map<String, Object> rtnMap = returnMap();
        try {
            //좋아요 등록 여부 확인
            if(articleMapper.countByUserIdxAndArticleIdx(articleLike)==1){
                rtnMap.put(RESULT_TEXT, RESULT_DUPLICATE);
            } else{
                articleMapper.insertArticleLike(articleLike);
                rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
            }
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }
    /**
     * FUNCTION :: 게시글 좋아요 삭제
     * @param articleLike
     * @return
     */
    @Transactional
    public String likeDelete(ArticleLike articleLike) {
        Map<String, Object> rtnMap = returnMap();
        try {
            articleMapper.deleteArticleLike(articleLike);
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 답글 등록
     * @param comment
     * @return
     */
    @Transactional
    public String commentSave(Comment comment) {
        Map<String, Object> rtnMap = returnMap();
        try {
            if(comment.getIdx() != null){   // 수정일 경우
                commentMapper.updateComment(comment);
            } else {    // 등록일 경우
                comment.setGroupLayer(comment.getGroupLayer()+1L);
                if(comment.getGroupLayer()!=0){//재답글인 경우
                    comment.setGroupOrd(commentMapper.maxGroupOrd(comment)+1L);
                    commentMapper.insertComment(comment);
                } else{ //답글인 경우
                    commentMapper.insertComment(comment);
                    commentMapper.updateOriginIdx(comment);

                }
            }
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }

        return jsonFormatTransfer(rtnMap);
    }
    /**
     * FUNCTION :: 답글 삭제
     * @param comment
     * @return
     */
    @Transactional
    public String commentDelete(Comment comment) {
        Map<String, Object> rtnMap = returnMap();
        try {
            commentMapper.updateDelYn(comment.getIdx());
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 답글 좋아요 등록
     * @param commentLike
     * @return
     */
    @Transactional
    public String commentLikeSave(CommentLike commentLike) {
        Map<String, Object> rtnMap = returnMap();
        try {
            //좋아요 등록 여부 확인
            if(commentMapper.countByUserIdxAndCommentIdx(commentLike)==1){
                rtnMap.put(RESULT_TEXT, RESULT_DUPLICATE);
            } else{
                commentMapper.insertCommentLike(commentLike);
                rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
            }
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }
    /**
     * FUNCTION :: 답글 좋아요 삭제
     * @param commentLike
     * @return
     */
    @Transactional
    public String commentLikeDelete(CommentLike commentLike) {
        Map<String, Object> rtnMap = returnMap();
        try {
            commentMapper.deleteCommentLike(commentLike);
            rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
        }catch (Exception e){
            log.error("Class ["+e.getClass()+"] Exception :: ",e);
            defaultExceptionHandling(rtnMap,RESULT_FAIL);
        }
        return jsonFormatTransfer(rtnMap);
    }
}
