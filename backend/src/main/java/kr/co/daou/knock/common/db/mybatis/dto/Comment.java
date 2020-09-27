package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Comment extends Paging {

    private Long idx;
    private Long articleIdx;
    private Long userIdx;
    private String contents;
    private String delYn;
    private Date regDate;
    private Long originIdx;
    private Long groupOrd;
    private Long groupLayer;

    private int commentLikeCount;

    public String getFormatedRegDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(this.regDate != null){
            return sdf.format(this.regDate);
        }else{
            return null;
        }
    }
}
