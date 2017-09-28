package org.lynn.storage;

import java.util.Collection;
import java.util.Map;

import org.lynn.util.common.model.Unique;

/**
 * 普通 java 实例与存储设备的数据结构之间的映射接口
 * 
 * @author lynn
 *
 * @param <ID>		主键类型
 * @param <MODEL>	java 内存中的实体类型
 */
public interface Mapper<ID, MODEL extends Unique<ID>>  {

	/**
	 * 插入数据
	 * 
	 * @param model
	 */
	void insert(MODEL model);
	
	/**
	 * 获取指定主键
	 * 
	 * @return
	 */
	MODEL getByID(ID id);
	
	/**
	 * 获取多个主键
	 * 
	 * @return
	 */
	Map<ID, MODEL> getByIds(Collection<ID> keys);
	
	/**
	 * 获取所有的数据
	 * 
	 * @return
	 */
	Map<ID, MODEL> getAll();
	
	/**
	 * 修改
	 * 
	 * @param model
	 */
	void update(MODEL model);
	
	/**
	 * 删除
	 * 
	 * @param key
	 */
	void delete(ID key);
}
