package com.youlanw.cms.repository.mysql;


/**
 * @author Nicolas.
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.cms.entity.mysql.Image;


@Component
public interface ImageMapper{
	/**
	 * 新增
	 * @param entity
	 */
	void insert(Image entity);
	/**
	 * 修改
	 * @param entity
	 */
	void update(Image entity);
	/**
	 * 删除
	 * @param entity
	 */
	void delete(java.lang.Integer id);
	/**
	 * 根据Id查询对象
	 * @param id
	 * @return
	 */
	Image getById(java.lang.Integer id);
	/**
	 * 查询所有数量计数
	 * @param param
	 * @return
	 */
	Long count();
	/**
	 * 根据条件查询所有数量集合
	 * @param param
	 * @return
	 */
	List<Image> findPage(Map<String, Object> param);
	/**
	 * 根据条件查询所有数量分页对象
	 * @param param
	 * @param pageBounds
	 * @return
	 */
	//PageList<Image> findPage(Map<String, Object> param, PageBounds pageBounds);
}
