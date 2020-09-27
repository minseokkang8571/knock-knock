package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class Hashtag extends Paging {

    private Long idx;
    private String tag;

}
