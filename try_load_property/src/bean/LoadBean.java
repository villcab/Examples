package bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import utils.FacesUtil;
import utils.Parameters;

/**
 * 
 * @author Dualbiz
 *
 */
@ManagedBean
@ViewScoped
public class LoadBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoadBean.class);
	
	private String title = "VISTA DEMO";
	
	@PostConstruct
	private void init() {
		try {
			log.info("Vista cargada correctamente");
			
		} catch (Exception e) {
			log.error("Error al carga la vista", e);
		}
	}
	
	public void mostrarValor() {
		log.info("Mostrando valor");
		try {
			String valor = Parameters.key_path;
			FacesUtil.infoMessage(valor);
//			String nameProperties = "try.properties";
//			String s = LoadBean.class.getClassLoader().getResource(nameProperties).getPath();
//			System.out.println(s);
			
//			File logDir = new File(System.getProperty("jboss.server.log.dir"));
//			String [] sss = logDir.list(); // etc etc
//			for (String a: sss) {
//				System.out.println(a);
//			}
			
		} catch (Exception e) {
			log.error("Error al cargar la ruta", e);
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

}
