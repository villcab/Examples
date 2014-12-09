package com.firstonesoft.primefacesexamples.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.apache.log4j.Logger;

import com.firstonesoft.primefacesexamples.model.User;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
@ManagedBean
@ViewScoped
public class ReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ReportBean.class);
	
	private List<User> users;
	
	private ServletContext servletContext;
	
	@PostConstruct
	private void init() {
		
		User u1 = new User();
		u1.setFirstname("Bismarck Villca Soliz");
		
		User u2 = new User();
		u2.setFirstname("Vanesa Alanoca Choque");
		
		User u3 = new User();
		u3.setFirstname("Mirian Alinas");
		
		users = new ArrayList<User>();
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
		
		servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		
		log.info("Cargando vista");
	}
	
	public void generarReporte() {
		try {
			String path = servletContext.getRealPath(File.separator + "resources" + 
													 File.separator + "reports" +
													 File.separator + "report_example.jasper");

			JRBeanCollectionDataSource jrcds = new JRBeanCollectionDataSource(users);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("var_titulo", "REPORTE DE VENTAS");
			
			byte[] bytes = JasperRunManager.runReportToPdf(path, parameters, jrcds);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream outStream = response.getOutputStream();
			outStream.write(bytes, 0 , bytes.length);
			outStream.flush();
			outStream.close();
			
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			log.error("Error al generar el reporte", e);
		}
	}
	
//	public void doPreview(ActionEvent event) {
//		JasperPrint jasperPrint;
//		try {
//			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(users);
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("var_titulo", "REPORTE DE VENTAS");
//			
//			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/report_example.jasper");
//			
//			jasperPrint = JasperFillManager.fillReport(reportPath, parameters, beanCollectionDataSource);
//			
//			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//			
//			JRHtmlExporter exporter = new JRHtmlExporter();
//			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//			exporter.exportReport();
//			FacesContext.getCurrentInstance().responseComplete();
//			
//			log.info("Reporte completo");
//			
//		} catch (Exception e) {
//			log.error("Error al hacer un preview", e);
//		}
//	}

	public void doExportPdf(ActionEvent event) {
		JasperPrint jasperPrint;
		try {
			String path = servletContext.getRealPath(File.separator + "resources" + 
					 File.separator + "reports" +
					 File.separator + "report_example.jasper");
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(users);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("var_titulo", "REPORTE DE VENTAS");
			
			jasperPrint = JasperFillManager.fillReport(path, parameters, beanCollectionDataSource);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			log.error("Error al generar el reporte", e);
		}
	}

	public void doExportXlsx(ActionEvent event) {
		JasperPrint jasperPrint;
		try {
			String path = servletContext.getRealPath(File.separator + "resources" + 
					 File.separator + "reports" +
					 File.separator + "report_example.jasper");
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(users);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("var_titulo", "REPORTE DE VENTAS");
			
			jasperPrint = JasperFillManager.fillReport(path, parameters, beanCollectionDataSource);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
			exporter.exportReport();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			log.error("Error al generar el reporte", e);
		}
	}

	public void doExportDocx(ActionEvent event) {
		JasperPrint jasperPrint;
		try {
			String path = servletContext.getRealPath(File.separator + "resources" + 
					 File.separator + "reports" +
					 File.separator + "report_example.jasper");
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(users);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("var_titulo", "REPORTE DE VENTAS");
			
			jasperPrint = JasperFillManager.fillReport(path, parameters, beanCollectionDataSource);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.docx");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
			exporter.exportReport();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			log.error("Error al generar el reporte", e);
		}
	}

	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
