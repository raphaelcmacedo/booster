package com.booster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "sequence", sequenceName = "seq_user")
public class User implements GenericEntity {
	
	@Id
	@Column(name="userid")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sequence")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="idathlete")
	private Athlete athlete;
	
	@ManyToOne
	@JoinColumn(name="idtrainer")
	private Trainer trainer;
	
	private String login;
	private String password;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Athlete getAthlete() {
		return athlete;
	}
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole(){
		if(this.getAthlete() != null){
			return "ATHLETE";
		}else{
			return "TRAINER";
		}

	}
	
	
	
}
