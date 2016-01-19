package com.booster.repository;

import java.io.Serializable;
import java.util.List;

import com.booster.entity.GenericEntity;

public interface GenericRepository {
	public void save(GenericEntity entity);
	public void delete(GenericEntity entity);
	public GenericEntity find(Serializable id);
	public List<GenericEntity> list(String where);
	
}
