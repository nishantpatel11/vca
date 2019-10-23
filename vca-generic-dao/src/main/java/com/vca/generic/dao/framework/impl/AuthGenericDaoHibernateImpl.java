/**
 * Class Name:			GenericDaoHibernateImpl
 * Created On:			5:36:08 PM, May 13, 2014
 *
 * Copyright (c) 2014 Cordis Technology. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.vca.generic.dao.framework.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vca.generic.dao.framework.GenericDao;



@Transactional(value = "authTransactionManager")
public class AuthGenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK>{
	
	@Autowired
	private SessionFactory				sessionFactory;

	private final Class<T>				type;

	public AuthGenericDaoHibernateImpl(Class<T> type) {

		this.type = type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PK create(T o) {

		Session session = sessionFactory.getCurrentSession();
		PK pk = (PK) session.save(o);
		session.flush();
		return pk;
	}


	@Override
	@SuppressWarnings({ "unchecked"})
	public List<PK> createBatch(List<T> oList) 
	{		
		Session session = sessionFactory.getCurrentSession();
		
		List<PK> pkList=new ArrayList<PK>();
		for(T o:oList)
		{
			pkList.add( (PK)session.save(o) );	
			
		}		
		session.flush();
		return pkList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T read(PK id) {

		Session session = sessionFactory.getCurrentSession();
		T t = (T) session.get(type, id);
		session.flush();
		return t;
	}

	@Override
	public void update(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.update(o);
		session.flush();
	}

	@Override
	public void updateBatch(List<T> transientObjectList) {
		
		Session session = sessionFactory.getCurrentSession();
		for(T o:transientObjectList)
		{
			session.update(o);
		}
		session.flush();
	}
	
	@Override
	public void saveOrUpdate(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.evict(o);
		session.flush();
		session.saveOrUpdate(o);
	}

	@Override
	public void merge(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.merge(o);
		session.flush();
	}

	@Override
	public void evict(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.evict(o);
		session.flush();
	}

	@Override
	public void flush() {

		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}

	@Override
	public void delete(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.delete(o);
		session.flush();
	}
	
	@Override
	public void deleteBatch(List<T> oList) {

		Session session = sessionFactory.getCurrentSession();
		for(T o : oList){
		session.delete(o);
		}
		session.flush();
	}

	
	@Override
	public List<Object> convertToObjectList(List<Object[]> objArrayList, Object object, List<String> excludedFieldNames, List<String> includedFieldNames)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		List<Object> ObjectList = new ArrayList<>();

		for (Object[] obj : objArrayList) {
			Class<?> cls = object.getClass();
			object = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();

			int i = 0;
			for (Field field : fields)
				if (includedFieldNames != null) {
					if (includedFieldNames.contains(field.getName())) {
						field.setAccessible(true);
						field.set(object, obj[i]);
						i++;
					}
				}
				else {
					if ("serialVersionUID".equalsIgnoreCase(field.getName()) || excludedFieldNames != null && excludedFieldNames.contains(field.getName()))
						continue;
					field.setAccessible(true);
					field.set(object, obj[i]);
					i++;
				}

			ObjectList.add(object);
		}

		return ObjectList;
	}

		public void setSessionFactory(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	 
		@Override
		public Object convertToObject(Object[] objArray, Object object, List<String> excludedFieldNames, List<String> includedFieldNames)
				throws ClassNotFoundException, InstantiationException, IllegalAccessException {

	 		
				Class<?> cls = object.getClass();
				object = cls.newInstance();
				Field[] fields = cls.getDeclaredFields();

				int i = 0;
				for (Field field : fields)
					if (includedFieldNames != null) {
						if (includedFieldNames.contains(field.getName())) {
							field.setAccessible(true);
							field.set(object, objArray[i]);
							i++;
						}
					}
					else {
						if ("serialVersionUID".equalsIgnoreCase(field.getName()) || excludedFieldNames != null && excludedFieldNames.contains(field.getName()))
							continue;
						field.setAccessible(true);
						field.set(object, objArray[i]);
						i++;
					}

			
			return object;
		}

		@Override
		public List<T> listEntityByIdList(List<PK> idList) {
			Session  session = sessionFactory.getCurrentSession();
			
				
				MultiIdentifierLoadAccess<T> multi = session.byMultipleIds(type);
				List<T> entityList = multi.multiLoad(idList);
				session.flush();
				
		      return entityList;
		   }
		
		
		
		
		 
		@Override
		public List<T> listEntityByParameter(String queryStatment, Map<String, Object> paramsKayAndValues) {
			Session  session = sessionFactory.getCurrentSession();
			
				
		      @SuppressWarnings("unchecked")
		      TypedQuery<T> query=session.createQuery(queryStatment);
		      if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty())
				   for(String key : paramsKayAndValues.keySet())
				   query.setParameter(key, paramsKayAndValues.get(key));
				  
		      session.flush();
				
		      return query.getResultList();
		   }
		
	
		

		 
		@Override
		public List<T> listEntity(String queryStatment) {
			Session  session = sessionFactory.getCurrentSession();
			
				
		      @SuppressWarnings("unchecked")
		      TypedQuery<T> query=session.createQuery(queryStatment);
		      
		      session.flush();
				
		      return query.getResultList();
		   }
		
	
		
		
		@Override
		public List<Object[]> listCompositeSqlQuery(String sqlQuery, Map<String,Object> paramsKayAndValues) {
		
			Session  session = sessionFactory.getCurrentSession();
			

			  
			@SuppressWarnings({ "rawtypes", "deprecation" })
			Query query = session.createSQLQuery(
					sqlQuery);
			   if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty())
			   for(String key : paramsKayAndValues.keySet())
			   query.setParameter(key, paramsKayAndValues.get(key));
			  
			   @SuppressWarnings("unchecked")
			List<Object[]> result = query.list();
			   session.flush();
				
		      return result;
		   }
		
		
		
		@Override
		public List<?> listSingleRowResult(String sqlQuery, Map<String,Object> paramsKayAndValues) {
			
			Session  session = sessionFactory.getCurrentSession();
			
			  
			@SuppressWarnings({ "rawtypes", "deprecation" })
			Query query = session.createSQLQuery(
					sqlQuery);
			   if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty())
			   for(String key : paramsKayAndValues.keySet())
			   query.setParameter(key, paramsKayAndValues.get(key));
			  
			   @SuppressWarnings("unchecked")
			List<?> result = query.list();
			   session.flush();
				
		      return result;
		   }
		
		@Override
		public Object[] listSqlQuerySingleResult( String sqlQuery, Map<String,Object> paramsKayAndValues) {

			Session  session = sessionFactory.getCurrentSession();
			
			    Query query = session.createSQLQuery(sqlQuery);
				   
				   if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty())
					   for(String key : paramsKayAndValues.keySet())
					   query.setParameter(key, paramsKayAndValues.get(key));
					
				   Object[] r = (Object[]) query.getSingleResult();
				 
				   session.flush();
					
				   return r;
			   }


	
}
