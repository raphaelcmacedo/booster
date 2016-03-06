package com.booster.controller;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.booster.entity.GenericEntity;
import com.booster.entity.User;
import com.booster.service.GenericService;

public abstract class GenericCrudMB<Entity extends GenericEntity, Service extends GenericService> implements Serializable {
	
	User entity = new User();
	List<GenericEntity> dataProvider = new ArrayList<GenericEntity>();
	private Service service;
 
	//construtor
	public GenericCrudMB(Service service){
		this.service = service;
		dataProvider = service.list();
		entity = new User();
	}
 
	//Métodos dos botões 
	public void save(ActionEvent actionEvent){
		service.save(entity);
		dataProvider = service.list();
		entity = new User();
	}
 
	
	public void delete(ActionEvent actionEvent){
		service.delete(entity);
		dataProvider = service.list();
		entity = new User();
	}

	public User getEntity() {
		return entity;
	}

	public void setEntity(User entity) {
		this.entity = entity;
	}

	public List<GenericEntity> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(List<GenericEntity> dataProvider) {
		this.dataProvider = dataProvider;
	}
 
	
}
