package com.booster.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.booster.entity.GenericEntity;
import com.booster.repository.GenericRepository;

public class GenericDAO<T extends GenericEntity> extends HibernateDaoSupport
implements GenericRepository {

	@Autowired
	public GenericDAO(@Qualifier("sessionFactory")
	SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	private Class<T> persistentClass;  
    public Class<T> getPersistentClass() {  
    	return this.persistentClass;  
    }  
	
	@Override
	public void delete(GenericEntity entity) {
		getHibernateTemplate().delete(entity);
		getHibernateTemplate().flush();
	}
	
	@Override
	public void save(GenericEntity entity){
		getHibernateTemplate().saveOrUpdate(entity);
		getHibernateTemplate().flush();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericEntity find(Serializable id){
		if(this.persistentClass == null){
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass()  
		            .getGenericSuperclass()).getActualTypeArguments()[0];
		}
		
		try{
			Long idLong = Long.parseLong(id.toString());
			return (GenericEntity) getHibernateTemplate().get(getPersistentClass(), idLong);
		}catch (Exception e){
			String idString = id.toString();
			return (GenericEntity) getHibernateTemplate().get(getPersistentClass(), idString);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GenericEntity> list(String where){
		if(this.persistentClass == null){
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass()  
		            .getGenericSuperclass()).getActualTypeArguments()[0];
		}
		Query query = this.getSession().createQuery("From " + getPersistentClass().getName() + where);
		return query.list();
	}

}