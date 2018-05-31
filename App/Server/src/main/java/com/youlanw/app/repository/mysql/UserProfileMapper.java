package com.youlanw.app.repository.mysql;


import org.springframework.stereotype.Component;

import com.youlanw.app.entity.mysql.UserProfile;

@Component
public interface UserProfileMapper{
	/**
	 * 插入对象
	 * @param entity
	 */
	void insert(UserProfile entity);
	/**
	 * 根据Id查询对象
	 * @param id
	 * @return
	 */
	UserProfile getById(java.lang.Integer id);
	
	/**
	 * 根据用户id查询对象
	 * @param userId
	 * @return
	 */
	UserProfile getByUserId(java.lang.Integer userId);
	/**
	 * 更新积分
	 * @param entity
	 */
	public void update(UserProfile entity);
	
	/**
	 * 根据userID 更新蓝币
	 * @param entity
	 */
	public void updateByUserId(UserProfile entity);
}
