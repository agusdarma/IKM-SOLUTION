package com.jakarta.software.plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.views.jasperreports.JasperReportConstants;
import org.apache.struts2.views.jasperreports.JasperReportsResult;
import org.apache.struts2.views.jasperreports.ValueStackDataSource;
import org.apache.struts2.views.jasperreports.ValueStackShadowMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class NewJasperReportPlugin extends StrutsResultSupport implements JasperReportConstants {

    private static final long serialVersionUID = -2523174799621182907L;

    private static final Logger LOG = LoggerFactory.getLogger(JasperReportsResult.class);

    protected String dataSource;
    protected String format;
    protected String documentName;
    protected String contentDisposition;
    protected String delimiter;
    protected String imageServletUrl = "/images/";
    protected String timeZone;

    /**
     * Connection which can be passed to the report
     * instead od dataSource.
     */
    protected String connection;

    /**
     * Names a report parameters map stack value, allowing
     * additional report parameters from the action.
     */
    protected String reportParameters;

    /**
     * Names an exporter parameters map stack value,
     * allowing the use of custom export parameters.
     */
    protected String exportParameters;

    /**
     * Default ctor.
     */
    public NewJasperReportPlugin() {
        super();
    }

    /**
     * Default ctor with location.
     *
     * @param location Result location.
     */
    public NewJasperReportPlugin(String location) {
        super(location);
    }

    public String getImageServletUrl() {
        return imageServletUrl;
    }

    public void setImageServletUrl(final String imageServletUrl) {
        this.imageServletUrl = imageServletUrl;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * set time zone id
     *
     * @param timeZone
     */
    public void setTimeZone(final String timeZone) {
        this.timeZone = timeZone;
    }

    public String getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(String reportParameters) {
        this.reportParameters = reportParameters;
    }

    public String getExportParameters() {
        return exportParameters;
    }

    public void setExportParameters(String exportParameters) {
        this.exportParameters = exportParameters;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        // Will throw a runtime exception if no "datasource" property. TODO Best place for that is...?
        initializeProperties(invocation);

        if (LOG.isDebugEnabled()) {
        	LOG.debug("Creating JasperReport for dataSource = " + dataSource + ", format = " + format);
        }

        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);

        // Handle IE special case: it sends a "contype" request first.
        // TODO Set content type to config settings?
        if ("contype".equals(request.getHeader("User-Agent"))) {
            try {
                response.setContentType("application/pdf");
                response.setContentLength(0);

                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.close();
            } catch (IOException e) {
                LOG.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
            return;
        }

        // Construct the data source for the report.
        ValueStack stack = invocation.getStack();
        ValueStackDataSource stackDataSource = null;

        Connection conn = (Connection) stack.findValue(connection);
        if (conn == null)
            stackDataSource = new ValueStackDataSource(stack, dataSource);

        // Determine the directory that the report file is in and set the reportDirectory parameter
        // For WW 2.1.7:
        //  ServletContext servletContext = ((ServletConfig) invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONFIG)).getServletContext();
        ServletContext servletContext = (ServletContext) invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONTEXT);
        String systemId = servletContext.getRealPath(finalLocation);
        Map parameters = new ValueStackShadowMap(stack);
        File directory = new File(systemId.substring(0, systemId.lastIndexOf(File.separator)));
        parameters.put("reportDirectory", directory);
        parameters.put(JRParameter.REPORT_LOCALE, invocation.getInvocationContext().getLocale());

        // put timezone in jasper report parameter
        if (timeZone != null) {
            timeZone = conditionalParse(timeZone, invocation);
            final TimeZone tz = TimeZone.getTimeZone(timeZone);
            if (tz != null) {
                // put the report time zone
                parameters.put(JRParameter.REPORT_TIME_ZONE, tz);
            }
        }

        // Add any report parameters from action to param map.
        Map reportParams = (Map) stack.findValue(reportParameters);
        if (reportParams != null) {
            if (LOG.isDebugEnabled()) {
        	LOG.debug("Found report parameters; adding to parameters...");
            }
            parameters.putAll(reportParams);
        }

        byte[] output;
        JasperPrint jasperPrint;

        // Fill the report and produce a print object
        try {
           // JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(systemId);
           JasperReport jasperReport = (JasperReport) JRLoader.loadObject(systemId);
            if (conn == null)
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stackDataSource);
            else
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
        } catch (JRException e) {
            LOG.error("Error building report for uri " + systemId, e);
            throw new ServletException(e.getMessage(), e);
        }

        // Export the print object to the desired output format
        try {
            if (contentDisposition != null || documentName != null) {
                final StringBuffer tmp = new StringBuffer();
                tmp.append((contentDisposition == null) ? "inline" : contentDisposition);

                if (documentName != null) {
                    tmp.append("; filename=");
                    tmp.append(documentName);
                    tmp.append(".");
                    tmp.append(format.toLowerCase());
                }

                response.setHeader("Content-disposition", tmp.toString());
            }

            JRExporter exporter;

            if (format.equals(FORMAT_PDF)) {
                response.setContentType("application/pdf");
                exporter = new JRPdfExporter();
            } else if (format.equals(FORMAT_CSV)) {
                response.setContentType("text/csv");
                exporter = new JRCsvExporter();
            } else if (format.equals(FORMAT_HTML)) {
                response.setContentType("text/html");

                // IMAGES_MAPS seems to be only supported as "backward compatible" from JasperReports 1.1.0

                Map imagesMap = new HashMap();
                request.getSession(true).setAttribute("IMAGES_MAP", imagesMap);

                exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imageServletUrl);

                // Needed to support chart images:
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
            } else if (format.equals(FORMAT_XLS)) {
                response.setContentType("application/vnd.ms-excel");
                exporter = new JRXlsExporter();
            } else if (format.equals(FORMAT_XML)) {
                response.setContentType("text/xml");
                exporter = new JRXmlExporter();
            } else if (format.equals(FORMAT_RTF)) {
                response.setContentType("application/rtf");
                exporter = new JRRtfExporter();
            } else {
                throw new ServletException("Unknown report format: " + format);
            }

            Map exportParams = (Map) stack.findValue(exportParameters);
            if (exportParams != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Found export parameters adding to exporter parameters", "");
                }
                exporter.getParameters().putAll(exportParams);
            }

            output = exportReportToBytes(jasperPrint, exporter);
        } catch (JRException e) {
            String message = "Error producing " + format + " report for uri " + systemId;
            LOG.error(message, e);
            throw new ServletException(e.getMessage(), e);
        }

        response.setContentLength(output.length);

        // Will throw ServletException on IOException.
        writeReport(response, output);
    }

    /**
     * Writes report bytes to response output stream.
     *
     * @param response Current response.
     * @param output   Report bytes to write.
     * @throws ServletException on stream IOException.
     */
    private void writeReport(HttpServletResponse response, byte[] output) throws ServletException {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(output);
            outputStream.flush();
        } catch (IOException e) {
            LOG.error("Error writing report output", e);
            throw new ServletException(e.getMessage(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                LOG.error("Error closing report output stream", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
    }

    /**
     * Sets up result properties, parsing etc.
     *
     * @param invocation Current invocation.
     * @throws Exception on initialization error.
     */
    private void initializeProperties(ActionInvocation invocation) throws Exception {
        if (dataSource == null && connection == null) {
            String message = "No dataSource specified...";
            LOG.error(message);
            throw new RuntimeException(message);
        }
        if (dataSource != null)
            dataSource = conditionalParse(dataSource, invocation);

        format = conditionalParse(format, invocation);
        if (StringUtils.isEmpty(format)) {
            format = FORMAT_PDF;
        }

        if (contentDisposition != null) {
            contentDisposition = conditionalParse(contentDisposition, invocation);
        }

        if (documentName != null) {
            documentName = conditionalParse(documentName, invocation);
        }

        reportParameters = conditionalParse(reportParameters, invocation);
        exportParameters = conditionalParse(exportParameters, invocation);
    }

    /**
     * Run a Jasper report to CSV format and put the results in a byte array
     *
     * @param jasperPrint The Print object to render as CSV
     * @param exporter    The exporter to use to export the report
     * @return A CSV formatted report
     * @throws net.sf.jasperreports.engine.JRException
     *          If there is a problem running the report
     */
    private byte[] exportReportToBytes(JasperPrint jasperPrint, JRExporter exporter) throws JRException {
        byte[] output;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        if (delimiter != null) {
            exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, delimiter);
        }

        exporter.exportReport();

        output = baos.toByteArray();

        return output;
    }


    
    
}
