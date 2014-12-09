package com.firstonesoft.primefacesexamples.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
@ManagedBean
@ViewScoped
public class ChangeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ChangeBean.class);

	private Boolean swUno;
	private Boolean swDos;

	@PostConstruct
	private void init() {
		swUno = true;
		swDos = false;
	}

	public void cambiar() {
		if (swUno) {
			swUno = false;
			swDos = true;
		} else {
			swUno = true;
			swDos = false;
		}

		log.info("uno: " + swUno);
		log.info("dos: " + swDos);
	}

	public Boolean getSwUno() {
		return swUno;
	}

	public void setSwUno(Boolean swUno) {
		this.swUno = swUno;
	}

	public Boolean getSwDos() {
		return swDos;
	}

	public void setSwDos(Boolean swDos) {
		this.swDos = swDos;
	}

}
