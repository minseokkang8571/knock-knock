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
     * @param model
     * @param article
     * @return
     */
    public String list(Model model, Article article) {
        int totalCount = articleMapper.countByDto(article);
        setDefaultPaging(model, article, totalCount);
        List<Article> articleList = articleMapper.findAllByDto(article);

        model.addAttribute("articleList", articleList);

        return "url";
    }

    /**
     * FUNCTION :: 게시글 등록 / 수정 서식
     * @param model
     * @param article
     * @return
     */
    public String form(Model model, Article article) {
        if(article.getIdx() != null){
            // LINE :: 수정인 경우 정보 가져와서 보내주기
            article = articleMapper.findByIdx(article.getIdx());
        }
        model.addAttribute("article", article);

        return "url";

    }

    /**
     * FUNCTION :: 게시글 상세
     * @param model
     * @param article
     * @return
     */
    public String view(Model model, Article article) {
        article = articleMapper.findByIdx(article.getIdx());
        List<Comment> comment = commentMapper.findAllByArticleIdx(article.getIdx());

        model.addAttribute("article", article);
        model.addAttribute("comment", comment);

        return "url";

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
            article.setIdx(articleMapper.insertByArticle(article));
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
