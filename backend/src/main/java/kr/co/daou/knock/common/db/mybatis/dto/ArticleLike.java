package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class ArticleLike extends Paging {

    private Long idx;
    private Long articleIdx;
    private Long userIdx;

}
