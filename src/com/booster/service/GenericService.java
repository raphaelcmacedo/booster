package com.booster.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.booster.entity.GenericEntity;
import com.booster.repository.GenericRepository;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service(value="GenericService")
public class GenericService {
		
	GenericRepository repository;
	
	public GenericService() {
	}

	public GenericService(GenericRepository repository) {
		this.repository = repository;
	}

	public void setRepository(GenericRepository repository) {
		this.repository = repository;
	}

	public void validate(GenericEntity entity)throws SQLException{
		
	}
	
	public Object save(GenericEntity entity){
		try{
			validate(entity);
			this.repository.save(entity);
			return null;
		}catch (Exception e) {
			return null;
		}
				
	}
	
	public Object delete(GenericEntity entity){
		try{
			this.repository.delete(entity);
			return null;
		}catch(Exception e){
			return null;
		}
				
	}
	
	public GenericEntity find(Serializable id){
		GenericEntity entity = this.repository.find(id);
		return entity;
	}
	
	public List<GenericEntity> list(){
		return this.repository.list("");		
	}
	
	public List<GenericEntity> list(String param){
		return this.repository.list(param);		
	}
		
}
