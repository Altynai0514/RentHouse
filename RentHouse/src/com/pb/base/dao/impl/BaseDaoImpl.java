package com.pb.base.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pb.base.dao.IBaseDao;
import com.pb.util.HibernateSessionFactory;
import com.pb.util.Page;

public class BaseDaoImpl<T> implements IBaseDao<T> {
	private Log log = LogFactory.getLog(this.getClass());
	private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
    	entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}	
	public void closeSession(){
		HibernateSessionFactory.closeSession();
	}

	@Override
	public void save(T instance) {
		log.debug("saving "+instance+" instance");
		Session session = getSession();
        Transaction tx = session.beginTransaction();
		try {
			session.save(instance);
			tx.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("save failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	@Override
	public void delete(T instance) {
		log.debug("deleting "+instance+" instance");
		Session session = getSession();
        Transaction tx = session.beginTransaction();
		try {
			session.delete(instance);
			tx.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	@Override
	public void update(T instance) {
		log.debug("updating "+instance+" instance");
		Session session = getSession();
        Transaction tx = session.beginTransaction();
		try {
			session.update(instance);
			tx.commit();
			log.debug("update successful");
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("update failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object findById(Class clazz, Integer id) {
		log.debug("getting "+clazz+" instance with id: " + id);
		try {
			//Hibernate.initialize(District.class);
		    
		    return null;
		} catch (RuntimeException re) {
		    re.printStackTrace();
			log.error("get failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		log.debug("getting "+entityClass+" List All ");
		try {
			String hql = "FROM "+entityClass;
			Query queryObject = getSession().createQuery(hql);
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by HQL ", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List findAll(Class clazz) {
		log.debug("getting "+clazz+" List All ");
		try {
			String c = clazz.toString();
			String hql = "FROM "+c.substring(c.lastIndexOf(".")+1,c.length());
			Query queryObject = getSession().createQuery(hql);
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by HQL ", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	/**
	 * 根据对象名和对象属性查询数据
	 * @param className
	 * @param propertyName
	 * @param value
	 * @return 实体对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String className,String propertyName, Object value) {
		log.debug("finding "+className+" instance with property: " + propertyName + ", value: " + value);
		
		try {
			String hql = "FROM "+className+" as o WHERE o."+propertyName+" = ?";
			Query queryObject = getSession().createQuery(hql);
			queryObject.setParameter(0, value);
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	/**
	 * 根据HQL查询数据
	 * @param hql
	 * @return 实体对象列表
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql) {
		log.debug(hql);
		try {
			Query queryObject = getSession().createQuery(hql);
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by HQL ", re);
			throw re;
		} finally{
			closeSession();
		}
	}
	
	/**
	 * 获取分页对象，实现分页获取数据的功能
	 * @param hql 查询数据的HQL
	 * @param page 需要显示数据的页码
	 * @param pageSize 每页数据量
	 * @return Object[0]当前页的数据列表List ,Object[1]总页数，Object[2]总数量
	 */
	@SuppressWarnings("rawtypes")
	public Object[] findPageByHql(final String hql, String hqlCount, final int page, final int pageSize) {
		log.debug(hql);
		List list = new ArrayList();
		Long total = new Long(0);
		Integer totalPage = 0;
		try {
			//1、根据hql语句查询指定数据
			Query qList = getSession().createQuery(hql);
			qList.setFirstResult((page>0?page-1:page) * pageSize);
			qList.setMaxResults(pageSize);
		    list = qList.list();
		    
			//2、根据hql语句查询总记录数
		    List listCount = findByHql(hqlCount);
			if(listCount!=null && listCount.size()>0){
				total = (Long)listCount.get(0);
			}
			
			//3、根据总记录数计算出总页数
			totalPage = (int) Math.ceil((double) total.longValue() / pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
		
		return new Object[] { list, totalPage, total };
	}
	
	/**
	 * 获取分页对象，实现分页获取数据的功能
	 * @param hql 查询数据的HQL
	 * @param page 分页对象
	 * @return page 分页对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page findPageByHql(final String hql, String hqlCount, Page page) {
		log.debug(hql);
		try {
			if(page !=null){
				//1、根据hql语句查询指定数据
				Query qList = getSession().createQuery(hql);
				qList.setFirstResult(page.getStartRow());
				qList.setMaxResults(page.getSize());
			    page.setList(qList.list());//将数据集合保存到page对象
			    
				//2、根据hql语句查询总记录数
			    List listCount = findByHql(hqlCount);
				if(listCount!=null && listCount.size()>0){
					page.setTotalCount(Integer.parseInt(String.valueOf(listCount.get(0))));//将总记录数保存到page对象
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
		
		return page;
	}
	
}