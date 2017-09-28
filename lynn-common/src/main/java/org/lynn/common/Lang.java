package org.lynn.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.lynn.common.enums.FileType;
import org.lynn.util.io.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lang {
	
	private static final Logger logger = LoggerFactory.getLogger(Lang.class);
	
	private static volatile boolean initial;
	private static Object lock = new Object();
	private static String locale = Locale.CHINA.toString();
	private static Map<String, String> languages = new HashMap<String, String>();

	public static String get(String key) {
		_tryInit();
		return languages.get(key);
	}
	
	private static final void _tryInit() {
		if (initial)
			return;
		synchronized (lock) {
			if (initial)
				return;
			_loadFromJar();
			_loadFromClasspath();
			initial = true;
		}
	}
	
	/**
	 * 读取系统配置的语言文件：默认为zh_CN
	 */
	private static void _loadFromJar() {
		InputStream in = null;
		Properties properties = new Properties();
		String fileName = "lang_" + locale + FileType.PROPERTIES.suffix();
		try {
			in = ResourceUtil.getResourceAsStream(Lang.class, "/conf/lang/" + fileName);
			properties.load(new InputStreamReader(in, Constants.UTF_8));
		} catch (FileNotFoundException | NullPointerException | NoSuchFileException e) {
			logger.warn("系统语言文件 - {} 不存在，将使用系统默认语言文件!", fileName);
			try {
				in = ResourceUtil.getResourceAsStream(Lang.class, "/conf/lang/lang_zh_CN.properties");
				properties.load(new InputStreamReader(in, Constants.UTF_8));
			} catch (Exception e1) {
				logger.error("系统默认语言文件加载失败，系统即将关闭......", e1);
				System.exit(1);
			}
		} catch (Exception e) {
			logger.error("系统语言文件 - {} 加载失败，系统即将关闭......", fileName, e);
			System.exit(1);
		}  finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {}
		}
		for (Entry<Object, Object> entry : properties.entrySet()) 
			languages.put(entry.getKey().toString(), entry.getValue().toString());
	}
	
	/**
	 * 读取用户的classpath下的语言文件，如果没有则什么都不做
	 * 如果在 classpath:conf/lang/lange_locale.properties 中定义了语言文件，且其中的key可以覆盖系统自定义的语言
	 */
	private static void _loadFromClasspath() {
		InputStream in = null;
		String fileName = "lang_" + locale + FileType.PROPERTIES.suffix();
		try {
			File file = ResourceUtil.getFile("classpath:conf/lang/" + fileName);
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(new InputStreamReader(in, Constants.UTF_8));
			for (Entry<Object, Object> entry : properties.entrySet()) 
				languages.put(entry.getKey().toString(), entry.getValue().toString());
		} catch (NullPointerException | FileNotFoundException | NoSuchFileException e) {
			logger.info("未指定本地语言文件 - {}！", fileName);
		} catch (Exception e) {
			logger.error("本地语言文件 - {} 加载失败，建议关闭服务器排查问题！", fileName, e);
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {}
		}
	}
	
	public static void setLocale(String locale) {
		Lang.locale = locale;
	}
}
