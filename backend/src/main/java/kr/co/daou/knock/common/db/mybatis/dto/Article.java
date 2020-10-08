package kr.co.daou.knock.common.db.mybatis.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class Article extends Paging {

    @ApiParam(value = "게시글 idx", required = true)
    private Long idx;
    @ApiParam(value = "작성자 idx", required = true)
    private Long userIdx;
    @ApiParam(value = "제목", required = true)
    private String title;
    @ApiParam(value = "내용", required = true)
    private String contents;
    @ApiParam(value = "삭제여부", required = true)
    private String delYn;
    @ApiParam(value = "등록일")
    private Date regDate;
    @ApiParam(value = "테그 리스트", required = true)
    private List<ArticleHashtag> articleHashtagList;

    @ApiParam(value = "작성자 이름")
    private String name;
    @ApiParam(value = "게시글 좋아요 여부")
    private boolean likeYn;
    @ApiParam(value = "태그 값")
    private String tag;

    public String getFormatedRegDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(this.regDate != null){
            return sdf.format(this.regDate);
        }else{
            return null;
        }
    }

}
