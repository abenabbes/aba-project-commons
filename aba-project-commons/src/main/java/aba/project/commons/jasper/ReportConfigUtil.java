/**
 * 
 */
package aba.project.commons.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author ali
 *
 */
public class ReportConfigUtil {

	//______ATTRIBUTS
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(ReportConfigUtil.class);
	
	/**
     * PRIVATE METHODS
     */
    public static void generateReport(String resourceFileJrxm, Map<String, Object> parametresReport, List<?> dataSource) {
    
    	// Rechargement du fichier .jrxml
    	JasperDesign jasperDesign = rechargementDuFichierJrxml(resourceFileJrxm);
    	
		//Compilation du fichier .jrxml en .jasper
		JasperReport jasperReport = compilerRapport(jasperDesign);
		
		//imprimer le rapport
		JasperPrint jasperPrint = fillReport(jasperReport, parametresReport, dataSource);
		 
		//Creation du rapport
    
    }
 
    public static JasperDesign rechargementDuFichierJrxml(String resourceFileJrxm){
    	
    	//Verification Existance de fichier
    	Assert.notNull(resourceFileJrxm, "le fichier JRXML n'existe pas");
    	
    	//Rechargement du fichier JRXML
    	JasperDesign jasperDesign = null;
    	
		try {
			jasperDesign = JRXmlLoader.load(resourceFileJrxm);
		} catch (JRException e) {
			logger.error("Erreur du chargement du fichier .jrxml");
		}
    	
    	return jasperDesign;
    }
    
    public static JasperReport compilerRapport(JasperDesign jasperDesign){
    	
    	//Verification Existance de fichier
    	Assert.notNull(jasperDesign, "le fichier JRXML n'existe pas");
    	
    	JasperReport jasperReport = null;
    	
    	try {
			//Compilation du fichier .jrxml en .jasper
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
		} catch (JRException e) {
			logger.error("Erreur de compilation de fichier");
		
		}
    	
    	return jasperReport ;
    }
    
    public static JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, List<?> dataSource) {
 
    	//Creation de JRDatasource 
    	JRDataSource jrDataSource = getJRDatasource(dataSource);
    	
    	//imprimer le rapport
    	JasperPrint jasperPrint = null;
		try {
		     jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
		} catch (JRException e) {
			logger.error("Erreur ????");
		}
 
        return jasperPrint;
    }
 
    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
 
        exporter.exportReport();
    }
    
    /**
     * Transformation de la liste de données (VO) en JRDataSource.
     * @param listeDataSource liste de données.
     * @return une instance de {@link JRDataSource}
     */
    private static JRDataSource getJRDatasource(List<?> listeDataSource){
    	JRDataSource dataSource = null;
    	if(listeDataSource == null){
    		return new JREmptyDataSource();
    	} else {
    		dataSource = new JRBeanCollectionDataSource(listeDataSource);
    	}
    	return dataSource;
    }
 
    public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/SampleReportJSF/servlets/image?image=");//SampleReportJSF is the name of the project
 
        exportReport(exporter, jasperPrint, out);
    }
 
    public static void exportReportAsExcel(JasperPrint jasperPrint, PrintWriter out) throws JRException, FileNotFoundException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        OutputStream outputfile = new FileOutputStream(new File("d:/output/JasperReport1.xls"));//make sure to have the directory. excel file will export here
         
        // coding For Excel:
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterXLS.exportReport();
        outputfile.write(output.toByteArray());
    }
}
