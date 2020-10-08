package kr.co.daou.knock.common.db.mybatis.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Language extends Paging {

    @ApiParam(value = "언어 idx", required = true)
    private Long iIdx;
    @ApiParam(value = "언어 이름", required = true)
    private String name;
    @ApiParam(value = "코드 idx", required = true)
    private Long codeIdx;

}
