package com.todo.ws.core.commands;

import com.todo.ws.core.setup.QueryUtil;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.commands.impl.ExportCommand;
import info.magnolia.context.Context;
import info.magnolia.jcr.util.NodeUtil;
import nl.vpro.jcr.criteria.query.*;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


public abstract class ExportCSVCommandAbstract extends ExportCommand {
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String FILE_NAME = "tickets.xls";


    private static final Logger log = LoggerFactory.getLogger(ExportCSVCommandAbstract.class);


    @Inject
    public ExportCSVCommandAbstract() {

    }


    public void exportDataAndOpenFileInBlankWindow(final String uuid) {

        final StreamResource.StreamSource source = () -> {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
                byte[] fileBytes = new byte[0];
                try {
                    Workbook excel = getAllTicketNodes(uuid);
                    try {
                        excel.write(bos);
                    } catch (IOException e) {
                        log.error("error generating locations excel report", e);
                    }
                    fileBytes = bos.toByteArray();
                    return new ByteArrayInputStream(fileBytes);

                } finally {
                    IOUtils.closeQuietly(bos);
                }
            } catch (final Exception e) {
                log.warn("Not able to create an InputStream from the OutputStream. Return null", e);
                return null;
            }
        };

        final StreamResource resource = new StreamResource(source, FILE_NAME);
        resource.setCacheTime(-1);
        resource.getStream().setParameter("Content-Disposition", "attachment; filename=" + FILE_NAME + "\"");
        resource.setMIMEType(CONTENT_TYPE);

        Page.getCurrent().open(resource, "", true);
    }

    private Workbook getAllTicketNodes(String uuid) throws RepositoryException {
        List<Node> ticketNodes = generateCSV("Ticket", "ticket");
        final Workbook workbook = new XSSFWorkbook();
        return generateCSVFile(workbook, uuid, ticketNodes);
    }

    private Workbook generateCSVFile(Workbook workbook, String uuid, List<Node> ticketNodes) throws RepositoryException {

        int rowsCounter = 0;
        final XSSFCellStyle defaultStyle = (XSSFCellStyle) workbook.createCellStyle();
        final XSSFFont fontStyleBold = (XSSFFont) workbook.createFont();
        fontStyleBold.setFontName("Arial Narrow");
        fontStyleBold.setFontHeightInPoints((short) 11);
        fontStyleBold.setBold(true);
        defaultStyle.setFont(fontStyleBold);
        writeHeaderCommonNames(workbook, rowsCounter, ticketNodes, defaultStyle);
        for (int i = 0; i < ticketNodes.size(); i++) {
            final Sheet sheet = workbook.createSheet("tickets");
            Row headerRow = sheet.createRow(rowsCounter++);
            Cell cell;
            final Node contentNode = ticketNodes.get(i);
            try {
                final PropertyIterator propertyIterator = contentNode.getProperties();
                while (propertyIterator.hasNext()) {
                    final Property p = propertyIterator.nextProperty();
                    final String name = p.getName();
                    final String val = p.getString();
                    if (name != null && val != null && !name.startsWith("jcr:") && !name.startsWith("mgnl:")) {
                        int namesPropertiesColumns = 0;
                        cell = headerRow.createCell(namesPropertiesColumns++);
                        final String valueStored = val.substring(0, 1)
                                .toUpperCase()
                                .concat(val.substring(1));
                        cell.setCellValue(valueStored);
                        cell.setCellStyle(defaultStyle);
                    }

                }
            } catch (final RepositoryException e) {
                log.error("error reading property for excel", e);
            }


            //CHECKSTYLE ON
        }
        return workbook;
    }

    private void writeHeaderCommonNames(Workbook workbook, int rowsCounter, List<Node> ticketNodes, XSSFCellStyle defaultStyle) {
        for (int i = 0; i < ticketNodes.size(); i++) {
            final Set<String> commonPropertiesNames = new HashSet<String>();

//            rowsCounter = 0;
            final Sheet sheet = workbook.createSheet("tickets");
            Row headerRow = sheet.createRow(rowsCounter);
            Cell cell;
            final Node contentNode = ticketNodes.get(i);
            try {
                final PropertyIterator propertyIterator = contentNode.getProperties();
                while (propertyIterator.hasNext()) {
                    final Property p = propertyIterator.nextProperty();
                    final String name = p.getName();
                    final String val = p.getString();
                    if (name != null && val != null && !name.startsWith("jcr:") && !name.startsWith("mgnl:")) {
                        commonPropertiesNames.add(name);
                    }

                }
            } catch (final RepositoryException e) {
                log.error("error reading property for excel", e);
            }

            headerRow = sheet.createRow(rowsCounter++);
            int namesPropertiesColumns = 0;
            for (final String nameProperty : commonPropertiesNames) {
                cell = headerRow.createCell(namesPropertiesColumns++);
                final String nameFormated = nameProperty.substring(0, 1)
                        .toUpperCase()
                        .concat(nameProperty.substring(1));
                cell.setCellValue(nameFormated);
                cell.setCellStyle(defaultStyle);
            }
        }
    }


    public List<Node> generateCSV(String nodeType, String workspace) {

        Session notificationsSession = QueryUtil.getGenericSession(workspace);
        Criteria notificationsCriteria = QueryUtil.getGenericCriteria(nodeType);
        AdvancedResult ar = notificationsCriteria.execute(notificationsSession);
        List<Node> ticketNodes = QueryUtil.advancedResultItemListToNodeList(IteratorUtils.toList(ar.getItems()));
        if (!ticketNodes.isEmpty()) {
            return ticketNodes;

        } else {
            return new ArrayList<>();
        }

    }


}