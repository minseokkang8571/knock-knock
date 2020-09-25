package kr.co.daou.knock.article.service;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.Comment;
import kr.co.daou.knock.common.db.mybatis.mapper.ArticleMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.CommentMapper;
import kr.co.daou.knock.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends CommonService implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * FUNCTION :: 게시글 리스트
     * @param article
     * @return
     */
    public String list(Article article) {
        Map<String, Object> rtnMap = returnMap();
        int totalCount = articleMapper.countByDto(article);
        setDefaultPaging(rtnMap, article, totalCount);
        List<Article> articleList = articleMapper.findAllByDto(article);
        rtnMap.put("articleList", articleList);
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS);
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 게시글 상세
     * @param article
     * @return
     */
    public String view(Article article) {
        Map<String, Object> rtnMap = returnMap();
        article = articleMapper.findByIdx(article.getIdx());

        int totalCount = commentMapper.countByDto(article);
        setDefaultPaging(rtnMap, article, totalCount);
        List<Comment> comment = commentMapper.findAllByArticleIdx(article);


        rtnMap.put("article", article);
        rtnMap.put("comment", comment);
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS);
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
        if(article.getIdx() != null){   // 수정일 경우
            articleMapper.updateByArticle(article);
        } else {    // 등록일 경우
            articleMapper.insertByArticle(article);
            article.setIdx(article.getIdx());
        }
        rtnMap.put("idx", article.getIdx());
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS);
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

        articleMapper.updateDelYn(article.getIdx());
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS);

        return jsonFormatTransfer(rtnMap);
    }
}
