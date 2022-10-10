package com.todo.ws.core.actions;

import info.magnolia.ui.api.action.CommandActionDefinition;

public class ExportActionDefinition extends CommandActionDefinition {

    public ExportActionDefinition() {
        setImplementationClass(ExportAction.class);

    }
}