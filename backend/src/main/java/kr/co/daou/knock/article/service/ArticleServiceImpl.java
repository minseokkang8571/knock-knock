package kr.co.daou.knock.article.service;

import kr.co.daou.knock.common.db.mybatis.dto.*;
import kr.co.daou.knock.common.db.mybatis.mapper.*;
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

    @Autowired
    private CodeMapper codeMapper;

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
            int totalCount = commentMapper.countByDto(article);
            setDefaultPaging(rtnMap, article, totalCount);
            List<Comment> comment = commentMapper.findAllByArticleIdx(article);

            article = articleMapper.findByDto(article);
            article.setArticleHashtagList(articleHashtagMapper.findAllByArticleIdx(article.getIdx()));

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
                //code, articleHashtag 테이블 데이터 삭제 후 재등록
                codeMapper.deleteByArticleIdx(article);
                articleHashtagMapper.deleteByArticleIdx(article);
            } else {    // 등록일 경우
                articleMapper.insertArticle(article);
            }
            int startIndex = article.getContents().indexOf("```");
            int endIndex=0;
            Code code = new Code();
            Language language = new Language();
            code.setArticleIdx(article.getIdx());
            while(startIndex!=-1){
                endIndex = article.getContents().indexOf("```",startIndex+1);
                //코드테이블 삽입
                code.setOriginContents(article.getContents().substring(startIndex,endIndex+3));
                language.setName(code.getOriginContents().split("\n")[0].substring(3));
                if(codeMapper.countByLanguage(language)>0){
                    codeMapper.insertCode(code);
                }
                startIndex = article.getContents().indexOf("```",endIndex+1);
            }

            //해시태그 추출
            if(article.getTag() != null) {
                String[] tagList = article.getTag().split(",");
                Hashtag hashtag = new Hashtag();
                for (int i = 0; i < tagList.length; i++) {
                    hashtag.setTag(tagList[i]);
                    //추출된 해시태그 hashtag 테이블에 있는지 확인
                    if (hashtagMapper.countByTag(hashtag) == 0) {
                        //해시태그가 없으면 tag 테이블에 추출된 해시태그 삽입
                        hashtagMapper.insertHashtag(hashtag);
                    } else {
                        hashtag = hashtagMapper.findByHashtag(hashtag);
                    }
                    //articleHashtag 테이블에 추출된 해시태그 정보 삽입
                    ArticleHashtag articleHashtag = new ArticleHashtag();
                    articleHashtag.setArticleIdx(article.getIdx());
                    articleHashtag.setHashtagIdx(hashtag.getIdx());
                    articleHashtagMapper.insertHashtag(articleHashtag);
                }
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
