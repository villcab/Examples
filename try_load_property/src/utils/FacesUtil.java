package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
public class FacesUtil {

	public static void infoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "INFORMACION"));
	}
	
	public static void warmMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "ADVERTENCIA"));
	}
	
	public static void errorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "ERROR"));
	}
	
	public static void fatalMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, "ERROR FATAL"));
	}

	public static Object getSessionAttribute(String attribute) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		Object o = null;
		if (session != null) {
			o = session.getAttribute(attribute);
		}
		return o;
	}

	public static void setSessionAttribute(String attribute, Object value) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.setAttribute(attribute, value);
	}

	public static void removeSessionAttribute(String attribute) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.removeAttribute(attribute);
	}

	public static void setParameter(String key, Object o) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam(key, o);
	}

	public static Object getParameter(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(key);
	}

	public static void showDialog(String widgetVarDialog) {
		String ejecutar = String.format("PF('%s').show()", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

	public static void hideDialog(String widgetVarDialog) {
		String ejecutar = String.format("PF('%s').hide()", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

}
