package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CommentLike extends Paging {

    private Long idx;
    private Long commentIdx;
    private Long userIdx;

}
