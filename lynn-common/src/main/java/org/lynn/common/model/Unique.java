package org.lynn.common.model;

import java.io.Serializable;

/**
 * 该接口表示的同一类型中对象的唯一性。一般指的是同一类对象中，同一个 ID 只能有一个实例
 * 
 * @author lynn
 *
 * @param <ID>
 */
public interface Unique<ID> extends Serializable {

	ID getId();
}
