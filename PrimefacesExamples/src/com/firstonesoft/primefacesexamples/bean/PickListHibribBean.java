package com.firstonesoft.primefacesexamples.bean;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static org.hamcrest.Matchers.equalTo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

import com.firstonesoft.primefacesexamples.model.User;
import com.firstonesoft.primefacesexamples.utils.FacesUtil;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
@ManagedBean
@ViewScoped
public class PickListHibribBean implements Serializable {

	private static final Logger log = Logger.getLogger(PickListHibribBean.class);
	private static final long serialVersionUID = 1L;
	
	private DualListModel<User> dualUsers;
	
	@PostConstruct
	private void init() {
		
		User u1 = new User();
		u1.setId(1);
		u1.setFirstname("Bismarck Villca Soliz");
		
		User u2 = new User();
		u2.setId(2);
		u2.setFirstname("Vanesa Alanoca Choque");
		
		User u3 = new User();
		u3.setId(3);
		u3.setFirstname("Mirian Alinas");
		
		List<User> source = new ArrayList<User>();
		List<User> target = new ArrayList<User>();
		
		source.add(u1);
		source.add(u2);
		source.add(u3);
		
		dualUsers = new DualListModel<User>(source, target);
		
		log.info("Cargando vista");
		
	}
	
	public void aumentar() {
		long id = Long.parseLong(FacesUtil.getParameter("id").toString());
		log.info("aumentando: " + id);
		
		User u = selectFirst(dualUsers.getTarget(), having(on(User.class).getId(), equalTo(id)));
		u.setAge(u.getAge() + 1);
		log.info("aumentando: " + u);
		
	}
	
	public void rebajar() {
		long id = Long.parseLong(FacesUtil.getParameter("id").toString());
		log.info("rebajando: " + id);
		
		User u = selectFirst(dualUsers.getTarget(), having(on(User.class).getId(), equalTo(id)));
		
		if (u.getAge() > 0) {
			u.setAge(u.getAge() - 1);
		}
		log.info("rebajando: " + u);
		
	}
	
	/*
	 * GETTER AND SETTER
	 */
	public DualListModel<User> getDualUsers() {
		return dualUsers;
	}
	
	public void setDualUsers(DualListModel<User> dualUsers) {
		this.dualUsers = dualUsers;
	}
	
}
