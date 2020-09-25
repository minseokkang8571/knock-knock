package kr.co.daou.knock.article.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.article.service.ArticleService;
import kr.co.daou.knock.common.db.mybatis.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * FUNCTION :: 게시글 리스트
     * @return
     */
    @ApiOperation(value = "게시글 리스트", response = List.class)
    @GetMapping("/list")
    public String list(Model model, Article article) {
        return articleService.list(model, article);
    }

    /**
     * FUNCTION :: 게시글 상세
     * @return
     */
    @ApiOperation(value = "게시글 상세", response = List.class)
    @GetMapping("/view")
    public String view(Model model,@RequestBody Article article) {
        return articleService.view(model, article);
    }

    /**
     * FUNCTION :: 게시글 등록 / 수정 서식
     * @return
     */
    @ApiOperation(value = "게시글 등록 / 수정 서식", response = List.class)
    @GetMapping("/form")
    public String form(Model model,@RequestBody Article article) {
        return articleService.form(model, article);
    }

    /**
     * FUNCTION :: 게시글 등록
     * @return
     */
    @ApiOperation(value = "게시글 등록", response = List.class)
    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody Article article) {
        return articleService.save(article);
    }

    /**
     * FUNCTION :: 게시글 수정
     * @return
     */
    @ApiOperation(value = "게시글 수정", response = List.class)
    @PatchMapping("/save")
    @ResponseBody
    public String modify(@RequestBody Article article) {
        return articleService.save(article);
    }

    /**
     * FUNCTION :: 게시글 삭제
     * @param article
     * @return
     */
    @ApiOperation(value = "게시글 삭제", response = List.class)
    @DeleteMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Article article) {
        return articleService.delete(article);
    }


}
