package com.firstonesoft.primefacesexamples.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import com.firstonesoft.primefacesexamples.model.User;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
@ManagedBean
@ViewScoped
public class UserWizardBean implements Serializable {

	private static final Logger log = Logger.getLogger(UserWizardBean.class);
	private static final long serialVersionUID = 1L;

	private User user = new User();

	private boolean skip;
	
	@PostConstruct
	private void init() {
		log.info("Cargando vista");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void save() {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :"
				+ user.getFirstname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
}
