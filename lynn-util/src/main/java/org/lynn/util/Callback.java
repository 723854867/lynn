package org.lynn.util;

public interface Callback<P, V> {

	V invoke(P param) throws Exception;
}
