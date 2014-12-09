package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
public class Parameters {
	
	public static final Logger log = Logger.getLogger(Parameters.class);
	
	// Nombre del archivo de propiedades
	protected final static String nameProperties = "try.properties";

	// Ruta utilizando la variable de entorno de jboss
	protected final static String pathJBoss = System.getProperty("jboss.server.base.dir") + File.separator + "try_load" + File.separator + nameProperties;
	
	// Ruta local para cargar el archivo de propiedades
	protected final static String pathLocal = Parameters.class.getClassLoader().getResource(nameProperties).getPath();
	
	static {
		init();
	}
	
	private static Properties p;
	
	public static String key_path = p.getProperty("key.path");
	
	private static void init() {
		
		// Primero intentamos cargar de la variable de entorno de jboss
		p = loadProperties(pathJBoss, "VARIABLE JBOSS");
		
		// Si  es null entonces no cargo de la variable de jboss y cargamos el archivo local
		if (p == null) {
			p = loadProperties(pathLocal, "VARIABLE LOCAL");
		}
		
	}
	
	
	/*
	 * Metodos para realizar el cargado del archivo de propiedades de la ruta de jboss o local del proyecto
	 */
	private static Properties loadProperties(String path, String origen) {
		InputStream stream = null;
		Properties properties = new Properties();
		try {
			stream = new FileInputStream(path);
			properties.load(stream);
			
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					log.error("Error al cerrar el cargado de bytes", e);
				}
			}
			
			log.info("Archivo de propiedades cargado correctamente de la (" + origen + ") ruta: " + path);
			
		} catch (IOException e) {
			log.error("Error al cargar el archivo de propiedades de la (" + origen + ") ruta: " + path + "\t\tCausa: " + e.getMessage());
			return null;
		}
		
		return properties;
	}
	
}
