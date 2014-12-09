package com.firstonesoft.primefacesexamples.bean;


import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.oracle.jrockit.jfr.EventToken;
 
@ManagedBean
@ViewScoped
public class ScheduleBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ScheduleBean.class);
	
	// hora de inicio y hoar fin
	private String minTime = "08:00:00";
	private String maxTime = "18:30:00";
	
	// para el horario libre
	private Date horaInicio;
	private Date horaFin;

	private ScheduleModel eventModel;
    
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    @PostConstruct
    public void init() {
    	
    	// inicializo las horas de inicio y fin
    	horaInicio = getHour(12, 30, 0);
    	horaFin = getHour(14, 0, 0);
    	
        eventModel = new DefaultScheduleModel();
        
        cargarTareas();
         
        lazyEventModel = new LazyScheduleModel() {
             
			private static final long serialVersionUID = 1L;

			@Override
            public void loadEvents(Date start, Date end) {
                Date random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
                 
                random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
            }
        };
        
        log.info("vista cargada correctamente");
        
    }
    
    // carga una lista de tareas de acuerdo del dia de hoy
    private void cargarTareas() {
    	
    	// cargo la primera tarea de 8:00 a 10:00 equivale a 2 horas
    	ScheduleEvent e = new DefaultScheduleEvent("Instalar cañerias", getHour(8, 0, 0), getHour(10, 0, 0), "prioridad_alta");
    	e.setId("1");
    	eventModel.addEvent(e);
    	
    	// segunda tarea de 10:30 a 12:30
    	e = new DefaultScheduleEvent("Instalar tigo start", getHour(10, 30, 0), getHour(12, 30, 0), "prioridad_media");
    	e.setId("2");
    	eventModel.addEvent(e);
    	
    	e = new DefaultScheduleEvent("Cabar pozo para la fibra optica", getHour(14, 0, 0), getHour(15, 30, 0), "prioridad_baja");
    	e.setId("3");
    	eventModel.addEvent(e);
    	
    	e = new DefaultScheduleEvent("contrato de la empresa intermediria para acera el pozo", getHour(16, 0, 0), getHour(18, 30, 0), "prioridad_media");
    	e.setId("4");
    	eventModel.addEvent(e);
    	
    }
    
    // obtiene una hora dada
    public Date getHour(int h, int m, int s) {
        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("GMT-4"));
        gc.set(GregorianCalendar.HOUR_OF_DAY, h);
        gc.set(GregorianCalendar.MINUTE, m);
        gc.set(GregorianCalendar.SECOND, s);
        return gc.getTime();
    }
    
    // si una hora esta entre los dos rangos
    public boolean entreRango(Date hora, Date minHora, Date maxHora) {
        return hora.getTime() > minHora.getTime() && hora.getTime() < maxHora.getTime();
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
    public String getMaxTime() {
		return maxTime;
	}
    
    public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
    
    public String getMinTime() {
		return minTime;
	}
    
    public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
    
    // cuando se mueve un evento
    public void onEventMove(ScheduleEntryMoveEvent event) {
    	
    	log.info("Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
    	
    	ScheduleEvent e = event.getScheduleEvent();
    	
    	log.info("Start Date: " + df.format(e.getStartDate()) + ", End Date: " + df.format(e.getEndDate()));
    	
    	boolean sw = entreRango(e.getStartDate(), horaInicio, horaFin);
    	
    	if (sw) {
    		log.info("esta en horario libre, no puede");
    		
    		ScheduleEvent evento = selectFirst(eventModel.getEvents(), having(on(ScheduleEvent.class).getId(), equalTo(e.getId())));
    		
    		GregorianCalendar gc = new GregorianCalendar();
    		gc.setTime(evento.getStartDate());
    		gc.add(GregorianCalendar.MINUTE, event.getMinuteDelta());
    		
    		GregorianCalendar gcc = new GregorianCalendar();
    		gcc.setTime(evento.getEndDate());
    		gcc.add(GregorianCalendar.MINUTE, event.getMinuteDelta());
    		
    		evento = new DefaultScheduleEvent(evento.getTitle(), evento.getStartDate(), evento.getEndDate());
    		
    		eventModel.updateEvent(evento);
    		
    		log.info("Start Date: " + df.format(evento.getStartDate()) + ", End Date: " + df.format(evento.getEndDate()));
    		
    	} else {
    		log.info("si puede por que no esta en horario libre");
    	}
    	
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
