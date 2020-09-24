package kr.co.daou.knock.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.daou.knock.common.db.mybatis.dto.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

public class ApplicationService {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String AJAX_RESULT_TEXT = "httpCode";

    // LINE :: AJAX 결과 코드 ====================================================================================================================================================
    public static final String AJAX_RESULT_SUCCESS = "200";         // 성공
    public static final String AJAX_RESULT_DUPLICATE = "300";       // 중복
    public static final String AJAX_RESULT_NODATA = "400";          // 데이터 없음
    public static final String AJAX_RESULT_ILLEGAL_STATE = "401";   // 유효하지 않은 접근
    public static final String AJAX_RESULT_AUTHFAIL = "402";        // 인증실패
    public static final String AJAX_RESULT_FAIL = "500";            // 실패
    public static final String AJAX_RESULT_OVERFLOW = "999";        // 다중 행 리턴

    /**
     * FUNCTION :: Ajax요청에 대한 리턴 맵 객체 선언
     *
     * @return
     */
    public HashMap<String, Object> returnMap(){
        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_FAIL);      /* 실패 */
        return rtnMap;
    }

    /**
     * FUNCTION :: jSon 형식 변환
     *
     * @param map
     * @return String
     */
    public String jsonFormatTransfer(Map<String, Object> map) {
        String rtnJson = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            rtnJson = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.info("jsonFormatTransfer["+e.getMessage()+"]");
        }

        return rtnJson;
    }

    /***
     * FUNCTION :: 공통 페이징 처리
     * @return
     */
    public void setDefaultPaging(Model model, Paging pagingExtendedObject, int totalCount) {
        pagingExtendedObject.setTotalCount(totalCount);  //전체 페이지수 세팅
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("paging", pagingExtendedObject);
    }

    /**
     * FUNCTION :: 기본 EXCEPTION 처리
     *      트랙잭션 결과를 항상 롤백하도록 처리
     *      입력된 결과 값을 결과 객체에 세팅
     * @param rtnMap
     * @param result
     */
    public void defaultExceptionHandling(Map<String, Object> rtnMap, String result) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        rtnMap.put(AJAX_RESULT_TEXT, result);
    }

}
