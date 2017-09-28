package org.lynn.storage.redis;

import redis.clients.jedis.JedisSentinelPool;

public class Redis {

	private JedisSentinelPool jedisPool;
	
	public void setJedisPool(JedisSentinelPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
