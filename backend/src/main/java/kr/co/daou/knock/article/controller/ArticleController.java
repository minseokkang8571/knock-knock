package kr.co.daou.knock.article.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.article.service.ArticleService;
import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.ArticleLike;
import kr.co.daou.knock.common.db.mybatis.dto.Comment;
import kr.co.daou.knock.common.db.mybatis.dto.CommentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
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
    public String list(Article article) {
        return articleService.list(article);
    }

    /**
     * FUNCTION :: 게시글 상세
     * @return
     */
    @ApiOperation(value = "게시글 상세", response = List.class)
    @GetMapping("/view")
    public String view(Article article) {
        return articleService.view(article);
    }

    /**
     * FUNCTION :: 답글 호출
     * @return
     */
    @ApiOperation(value = "답글 호출", response = List.class)
    @GetMapping("/commentCall")
    public String commentCall(Article article) {
        return articleService.commentCall(article);
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
    public String delete(Article article) {
        return articleService.delete(article);
    }

    /**
     * FUNCTION :: 게시글 좋아요 등록
     * @return
     */
    @ApiOperation(value = "게시글 좋아요 등록", response = List.class)
    @PostMapping("/likeSave")
    @ResponseBody
    public String likeSave(@RequestBody ArticleLike articleLike) {
        return articleService.likeSave(articleLike);
    }

    /**
     * FUNCTION :: 게시글 좋아요 삭제
     * @param articleLike
     * @return
     */
    @ApiOperation(value = "게시글 좋아요 삭제", response = List.class)
    @DeleteMapping("/likeDelete")
    @ResponseBody
    public String likeDelete(@RequestBody ArticleLike articleLike) {
        return articleService.likeDelete(articleLike);
    }

    /**
     * FUNCTION :: 답글 등록
     * @return
     */
    @ApiOperation(value = "답글 등록", response = List.class)
    @PostMapping("/commentSave")
    @ResponseBody
    public String commentSave(@RequestBody Comment comment) {
        return articleService.commentSave(comment);
    }

    /**
     * FUNCTION :: 답글 수정
     * @return
     */
    @ApiOperation(value = "답글 수정", response = List.class)
    @PatchMapping("/commentSave")
    @ResponseBody
    public String commentModify(@RequestBody Comment comment) {
        return articleService.commentSave(comment);
    }

    /**
     * FUNCTION :: 답글 삭제
     * @param comment
     * @return
     */
    @ApiOperation(value = "답글 삭제", response = List.class)
    @DeleteMapping("/commentDelete")
    @ResponseBody
    public String commentDelete(@RequestBody Comment comment) {
        return articleService.commentDelete(comment);
    }

    /**
     * FUNCTION :: 답글 좋아요 등록
     * @return
     */
    @ApiOperation(value = "답글 좋아요 등록", response = List.class)
    @PostMapping("/commentLikeSave")
    @ResponseBody
    public String commentLikeSave(@RequestBody CommentLike commentLike) {
        return articleService.commentLikeSave(commentLike);
    }

    /**
     * FUNCTION :: 답글 좋아요 삭제
     * @param commentLike
     * @return
     */
    @ApiOperation(value = "답글 좋아요 삭제", response = List.class)
    @DeleteMapping("/commentLikeDelete")
    @ResponseBody
    public String commentLikeDelete(@RequestBody CommentLike commentLike) {
        return articleService.commentLikeDelete(commentLike);
    }
}
