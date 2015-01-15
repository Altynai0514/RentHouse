package com.pb.base.dao;

import java.util.List;

import org.hibernate.Session;

import com.pb.util.Page;

public interface IBaseDao<T> {
	public Session getSession();
	
	public void closeSession();
	
	public void save(T instances);
	
	public void delete(T instances);
	
	public void update(T instances);

	@SuppressWarnings("rawtypes")
	public Object findById(Class clazz ,Integer id) ;

	/**
	 * 查询所有用户列表
	 */
	public List<T> findAll();

	/**
	 * 查询所有用户列表
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> findAll(Class clazz);

	/**
	 * 根据HQL查询数据
	 * @param hql
	 * @return 实体对象列表
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql) ;

	/**
	 * 根据对象名和对象属性获取对象集合
	 * @param className
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findByProperty(String className,String propertyName, Object value);

	/**
	 * 实现分页获取数据的功能
	 * @param hql 查询数据的HQL
	 * @return Object[0]当前页的数据列表List、Object[1]总页数、Object[2]总记录数
	 */
	public Object[] findPageByHql(final String hql, String hqlCount, final int page, final int pageSize);
	
	/**
	 * 获取分页对象，实现分页获取数据的功能
	 * @param hql 查询数据的HQL
	 * @return Page对象 包含当前页的数据列表List、总页数、总数量和页数集合
	 */
	public Page findPageByHql(final String hql, String hqlCount, Page page);
}


