package org.lynn.util.common.serializer;

import java.io.IOException;

import org.lynn.util.common.Consts;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface SerializeUtil {

	class Jackson {
		public static final ObjectMapper SERIALIZER = new ObjectMapper();

		static {
			SERIALIZER.setSerializationInclusion(Include.NON_EMPTY);
			SERIALIZER.configure(Feature.STRICT_DUPLICATE_DETECTION, true); // 不允许有重复键值
		}

		public static String serial(Object object) {
			try {
				return SERIALIZER.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}

		public static <T> T deserial(String content, Class<T> clazz) {
			try {
				return SERIALIZER.readValue(content, clazz);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	class RedisUtil {
		public static final byte[] encode(Object value) {
			return (value instanceof byte[]) ? (byte[]) value : _encode(value.toString());
		}
		public static final byte[][] encode(Object... params) {
			byte[][] buffer = new byte[params.length][];
			for (int i = 0, len = params.length; i < len; i++) {
				if (params[i] instanceof byte[])
					buffer[i] = (byte[]) params[i];
				else
					buffer[i] = _encode(params[i].toString());
			}
			return buffer;
		}
		
		private static final byte[] _encode(String value) {
			return value.getBytes(Consts.UTF_8);
		}
	}
}
