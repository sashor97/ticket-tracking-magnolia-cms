package com.todo.ws.core.actions;

import com.todo.ws.core.commands.ExportCSVAllTicketsCommand;
import com.vaadin.server.Page;
import info.magnolia.commands.CommandsManager;
import info.magnolia.context.Context;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.importexport.DataTransporter;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.contentapp.action.JcrCommandAction;
import info.magnolia.ui.contentapp.async.AsyncActionExecutor;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.framework.action.AbstractCommandAction;
import info.magnolia.ui.framework.util.TempFileStreamResource;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;

import javax.inject.Inject;
import javax.jcr.Node;

/**
 * Triggers configured command.
 */
public class ExportAction  extends AbstractCommandAction<ExportActionDefinition> {

    private TempFileStreamResource tempFileStreamResource;

    @Inject
    public ExportAction(ExportActionDefinition definition, JcrItemAdapter item, CommandsManager commandsManager, UiContext uiContext, SimpleTranslator i18n) throws ActionExecutionException {
        super(definition, item, commandsManager, uiContext, i18n);
    }
//    protected void onPreExecute() throws Exception {
//        this.tempFileStreamResource = new TempFileStreamResource();
//        this.tempFileStreamResource.setTempFileName(this.getCurrentItem().getItemId().getUuid());
//        this.tempFileStreamResource.setTempFileExtension("xlsx");
//        super.onPreExecute();
//    }
//
//    protected void onPostExecute() throws Exception {
//        ExportCSVAllTicketsCommand exportCommand = (ExportCSVAllTicketsCommand)this.getCommand();
//        this.tempFileStreamResource.setFilename("tickets");
//        this.tempFileStreamResource.setMIMEType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        Page.getCurrent().open(this.tempFileStreamResource, "", true);
//    }


}
