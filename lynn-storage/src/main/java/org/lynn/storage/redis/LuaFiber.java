package org.lynn.storage.redis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.lynn.util.Callback;
import org.lynn.util.common.enums.FileType;
import org.lynn.util.common.serializer.SerializeUtil;
import org.lynn.util.io.FileReader;
import org.lynn.util.io.ResourceUtil;
import org.lynn.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisNoScriptException;

/**
 * lua 执行器
 * 
 * @author lynn
 */
class LuaFiber {
	
	private static final Logger logger = LoggerFactory.getLogger(LuaFiber.class);

	private Redis redis;
	private Map<String, Script> scripts = new ConcurrentHashMap<String, Script>();
	
	LuaFiber(Redis redis) {
		this.redis = redis;
		try {
			_initSystemScript();
			_initCustomScript();
		} catch (Exception e) {
			logger.error("lua 脚本初始化失败，系统即将关闭...", e);
			System.exit(1);
		}
	}
	
	/**
	 * 初始化系统自定义的 lua 脚本
	 * @throws  
	 * @throws MalformedURLException 
	 */
	private void _initSystemScript() throws Exception {
		for (LuaCmd cmd : LuaCmd.values()) {
			String fileName = cmd.name().toLowerCase() + FileType.LUA.suffix();
			BufferedInputStream in = null;
			byte[] buffer = null;
			try {
				in = new BufferedInputStream(ResourceUtil.getResourceAsStream(LuaFiber.class, "/conf/lua/" + fileName));
				buffer = new byte[in.available()];
				in.read(buffer);
				_addScript(cmd.name(), buffer);
			} finally {
				if (null != in)
					in.close();
			}
		}
	}
	
	private void _initCustomScript() throws Exception {
		File file = ResourceUtil.getFile("/conf/lua");
		if (!file.exists() || !file.isDirectory()) 
			return;
		FileReader.ergodic(file, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return file.isFile() && name.endsWith(FileType.LUA.suffix());
			}
		}, new Callback<File, Void>() {
			@Override
			public Void invoke(File file) throws Exception {
				_addScript(file.getName().replaceAll(FileType.LUA.suffix(), StringUtil.EMPTY), FileReader.bufferRead(file));
				return null;
			}
		});
	}
	
	private void _addScript(String cmd, byte[] content) { 
		Script script = new Script(content);
		if (null != scripts.putIfAbsent(cmd.toLowerCase(), script))
			logger.warn("存在多个同名的 lua 脚本 - {}！", cmd);
	}
	
	@SuppressWarnings("unchecked")
	<T> T invokeScript(ILuaCmd cmd, int keyNum, Object... params) throws Exception {
		Script script = scripts.get(cmd.name().toLowerCase());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.name() + " not exist!");
		byte[][] arr = SerializeUtil.RedisUtil.encode(params);
		return redis.invoke(new Callback<Jedis, T>() {
			@Override
			public T invoke(Jedis jedis) throws Exception {
				if (script.stored) {
					try {
						return (T) jedis.evalsha(SerializeUtil.RedisUtil.encode(script.sha1Key), keyNum, arr);
					} catch (JedisNoScriptException e) {
						logger.warn("redis lua 脚本 - {} 缓存未命中,直接执行脚本！", cmd.name());
					}
				}
				T object = (T) jedis.eval(SerializeUtil.RedisUtil.encode(script.content), keyNum, arr);
				script.stored = true;
				return object;
			}
		});
	}
	
	/**
	 * lua 脚本
	 * 
	 * @author lynn
	 */
	private class Script {
		private String sha1Key;
		private String content;
		private boolean stored;
		private Script(byte[] content) {
			this.sha1Key = DigestUtils.sha1Hex(content);
			this.content = new String(content);
		}
	}
}
