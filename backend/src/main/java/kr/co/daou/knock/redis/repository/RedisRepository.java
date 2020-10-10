package kr.co.daou.knock.redis.repository;

import org.springframework.data.repository.CrudRepository;

import kr.co.daou.knock.common.db.mybatis.dto.Token;

public interface RedisRepository extends CrudRepository<Token, Long>{

}
