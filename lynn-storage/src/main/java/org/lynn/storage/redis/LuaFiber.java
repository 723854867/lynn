package org.lynn.storage.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * lua 执行器
 * 
 * @author lynn
 */
class LuaFiber {

	private Map<String, Script> scripts = new HashMap<String, Script>();
	
	/**
	 * lua 脚本
	 * 
	 * @author lynn
	 */
	private class Script {
		private String sha1Key;
		private String content;
		private boolean stored;
	}
}
