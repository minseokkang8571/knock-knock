package kr.co.daou.knock.article.service;

import kr.co.daou.knock.common.db.mybatis.dto.Article;
import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
public interface ArticleService {
	String list(Article article);
	String view(Article article);
	String save(Article article);
	String delete(Article article);
}
