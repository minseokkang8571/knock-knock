package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Article extends Paging {

    private Long idx;
    private Long userIdx;
    private String title;
    private String contents;
    private String delYn;
    private Date regDate;

    public String getFormatedRegDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(this.regDate != null){
            return sdf.format(this.regDate);
        }else{
            return null;
        }
    }

}
