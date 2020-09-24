package kr.co.daou.knock.article.controller;

import kr.co.daou.knock.article.service.ArticleService;
import kr.co.daou.knock.common.db.mybatis.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * FUNCTION :: 게시글 리스트
     * @return
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model, Article article) {
        return articleService.list(model, article);
    }

    /**
     * FUNCTION :: 게시글 상세
     * @return
     */
    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String view(Model model, Article article) {
        return articleService.view(model, article);
    }

    /**
     * FUNCTION :: 게시글 등록 / 수정 서식
     * @return
     */
    @RequestMapping(value = {"/form"}, method = RequestMethod.GET)
    public String form(Model model, Article article) {
        return articleService.form(model, article);
    }

    /**
     * FUNCTION :: 게시글 등록
     * @return
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public String save(Article article) {
        return articleService.save(article);
    }

    /**
     * FUNCTION :: 게시글 수정
     * @return
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.PATCH)
    @ResponseBody
    public String modify(Article article) {
        return articleService.save(article);
    }

    /**
     * FUNCTION :: 게시글 삭제
     * @param article
     * @return
     */
    @RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Article article) {
        return articleService.delete(article);
    }


}
