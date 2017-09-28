package org.lynn.storage.redis;

import static org.lynn.util.common.serializer.SerializeUtil.RedisUtil.encode;

import org.lynn.util.Callback;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class Redis {

	private LuaFiber luaFiber;
	private JedisSentinelPool jedisPool;
	
	public Redis() {
		this.luaFiber = new LuaFiber(this);
	}
	
	public long hset(Object key, Object field, Object value) throws Exception { 
		return invoke(new Callback<Jedis, Long>() {
			@Override
			public Long invoke(Jedis jedis) {
				return jedis.hset(encode(key), encode(field), encode(value));
			}
		});
	}
	
	<T> T invoke(Callback<Jedis, T> invoke) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return invoke.invoke(jedis);
		} finally {
			jedis.close();
		}
	}
	
	public void setJedisPool(JedisSentinelPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
