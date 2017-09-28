package org.lynn.storage.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.JedisSentinelPool;

public class RedisTest {

	public static void main(String[] args) throws Exception {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("101.37.30.26:26379");
		sentinels.add("101.37.30.26:26380");
		sentinels.add("101.37.30.26:26381");
		JedisSentinelPool pool = new JedisSentinelPool("btkj-test", sentinels, "hzbtkj001");
		
		Redis redis = new Redis();
		redis.setJedisPool(pool);
		redis.hset("hello", "1", "11");
	}
}
