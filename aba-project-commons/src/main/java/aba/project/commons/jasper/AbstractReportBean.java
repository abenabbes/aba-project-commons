/**
 * 
 */
package aba.project.commons.jasper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

/**
 * <p>AbstractReportBean is an abstract java class,
 * containing the prepareReport() method and abstract methods.</p>
 * 
 * @author ali
 *
 */
public abstract class AbstractReportBean {

	/**
	 * Enumertion de type de rapport a generer.
	 * @author ali
	 *
	 */
	public enum ExportOption {
        PDF, HTML, EXCEL, RTF
    }
	
	/** Instance de l'enum*/
	private ExportOption exportOption;
	/** Resource*/
    private final String COMPILE_DIR = "/report/design/";
    //private String compileFileName = "productlist";//name of your compiled report file
    private String message;
 
    /** Paramètres à envoyer au rapport*/
    abstract protected Map<String,Object> ajouterParameterAuRapport();
    
    /**
     * Constructor.
     */
    public AbstractReportBean() {
        super();
        setExportOption(ExportOption.PDF);
    }

    //________METHODES
    
    /**
     * 
     * @throws JRException
     * @throws IOException
     */
    protected void prepareReport() throws JRException, IOException {
       
    	// TODO : Appel a la ccreation du rapport
//    	ReportConfigUtil.generateReport(resourceFileJrxm, parametresReport, dataSource);
    }
    
    //____GETTER && SETTER
    public ExportOption getExportOption() {
        return exportOption;
    }
 
    public void setExportOption(ExportOption exportOption) {
        this.exportOption = exportOption;
    }
 
    protected Map<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }
 
    protected String getCompileDir() {
        return COMPILE_DIR;
    }
 
    protected abstract String getCompileFileName();
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}
