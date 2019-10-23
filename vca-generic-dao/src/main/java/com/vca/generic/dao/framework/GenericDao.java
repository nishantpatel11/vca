package com.vca.generic.dao.framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Field;

public interface GenericDao<T, PK extends Serializable> {
	/**
	 * @param newInstance
	 * @return primary key
	 */
	public PK create(T newInstance);

	/**
	 * @param id
	 * @return model object
	 */
	public T read(PK id);

	/**
	 * @param transientObject
	 */
	default void update(T transientObject) {

	}

	/**
	 * 
	 * @param transientObjectList
	 */
	default void updateBatch(List<T> transientObjectList) {

	}

	/**
	 * @param object
	 */

	default void saveOrUpdate(T object) {

	}

	/**
	 * @param object
	 */
	default void merge(T object) {

	}

	/**
	 * @param object
	 */

	default void evict(T object) {

	}

	default void flush() {

	}

	/**
	 * @param persistentObject
	 */
	default void delete(T persistentObject) {

	}

	/**
	 *
	 * @param objArrayList
	 * @param object
	 * @param excludedFieldNames
	 * @param includedFieldNames
	 * @return List<Object>
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	default List<Object> convertToObjectList(List<Object[]> objArrayList, Object object,
			List<String> excludedFieldNames, List<String> includedFieldNames)
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
				} else {
					if ("serialVersionUID".equalsIgnoreCase(field.getName())
							|| excludedFieldNames != null && excludedFieldNames.contains(field.getName()))
						continue;
					field.setAccessible(true);
					field.set(object, obj[i]);
					i++;
				}

			ObjectList.add(object);
		}

		return ObjectList;

	}

	/**
	 * 
	 * @param oList
	 * @return
	 */
	public List<PK> createBatch(List<T> oList);

	default void deleteBatch(List<T> oList) {

	}
	
	
	default Object convertToObject(Object[] objArray, Object object, List<String> excludedFieldNames,
			List<String> includedFieldNames)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		return null;
	}

	
	default List<Object[]> listCompositeSqlQuery(String sqlQuery, Map<String, Object> paramsKayAndValues){
		
		return null;
	}

	default Object[] listSqlQuerySingleResult(String sqlQuery, Map<String, Object> paramsKayAndValues){
		return null;
	}

	default List<T> listEntityByIdList(List<PK> idList){
		
		return null;
	}

	default List<T> listEntityByParameter(String queryStatment, Map<String, Object> parameters){
		return null;
	}

	default List<T> listEntity(String queryStatment){
		
		return null;
	}

	List<?> listSingleRowResult(String sqlQuery, Map<String, Object> paramsKayAndValues);

}
