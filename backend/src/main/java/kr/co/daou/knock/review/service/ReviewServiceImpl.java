package kr.co.daou.knock.review.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;
import kr.co.daou.knock.common.db.mybatis.mapper.ChatMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.ReviewMapper;
import kr.co.daou.knock.common.service.CommonService;

@Service
public class ReviewServiceImpl extends CommonService implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	@Autowired
	private ChatMapper chatMapper;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String createRoom(Room room) {
		Map<String, Object> rtnMap = returnMap();
		try {
			if (reviewMapper.createRoom(room) == 1) {
				long roomIdx = room.getIdx();
				rtnMap.put("roomIdx", roomIdx);
				reviewMapper.copyCode(room.getArticleIdx());
				List<Code> list = reviewMapper.getCode(room.getArticleIdx());
				rtnMap.put("codeList", list);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

	@Override
	public String getRoom(Room room) {
		Map<String, Object> rtnMap = returnMap();
		try {
			int totalCount = reviewMapper.countByDto(room);
			setDefaultPaging(rtnMap, room, totalCount);
			List<Room> roomList = reviewMapper.findAllByDto(room);

			if(roomList.size() > 0) {
				rtnMap.put("roomList", roomList);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		
		return jsonFormatTransfer(rtnMap);
	}

	@Override
	public String enterRoom(long roomIdx) {
		Map<String, Object> rtnMap = returnMap();
		try {
			List<Code> codeList = reviewMapper.enterRoom(roomIdx);
			List<Chat> chatList = chatMapper.getChat(roomIdx);
			if (codeList.size() > 0) {
				rtnMap.put("codeList", codeList);
				rtnMap.put("chatList", chatList);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}			
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

	@Override
	public String modifyCode(Review review) {
		Map<String, Object> rtnMap = returnMap();
		try {
			if (reviewMapper.modifyCode(review) == 1 && reviewMapper.reviewLog(review) == 1) {
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}			
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

	@Override
	public String saveCode(long roomIdx) {
		Map<String, Object> rtnMap = returnMap();
		Map<String, Object> userMap = new HashMap<String, Object>();
		ListOperations<String, Object> lop = redisTemplate.opsForList();
//		SetOperations<String, Object> sop = redisTemplate.opsForSet();
		try {
			if (reviewMapper.saveCode(roomIdx) == 1) {
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
//				Set<Object> set = sop.members("idx");
				List<Object> chatList = new ArrayList<>();
				chatList = lop.range("chat:" + roomIdx, 0, -1);
				// list를 제대로 가져왔다면 redis에서 데이터 삭제
				if(chatList.size() > 0) {
					redisTemplate.delete("chat:" + roomIdx);
				}
				// chatList rdb에 저장
				userMap.put("chatList", chatList);
				reviewMapper.saveChat(userMap);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}			
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}
	
	@Override
	public String sendChat(Chat chat) {
		Map<String, Object> rtnMap = returnMap();
		try {
			//redis에 데이터 저장
			ListOperations<String, Object> lop = redisTemplate.opsForList();
//			SetOperations<String, Object> sop = redisTemplate.opsForSet();
//			sop.add("idx", chat.getRoomIdx());
			lop.rightPush("chat:" + chat.getRoomIdx(), chat);
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

}
