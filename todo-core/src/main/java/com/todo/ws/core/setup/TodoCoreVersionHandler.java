package com.todo.ws.core.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleResource;
import info.magnolia.module.delta.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is optional and lets you manage the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class TodoCoreVersionHandler extends DefaultModuleVersionHandler {
    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        ArrayList<Task> tasks = new ArrayList<>();

        return tasks;
    }


    @Override
    protected List<Task> getStartupTasks(final InstallContext installContext) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new BootstrapSingleResource("license", "license", "/mgnl-bootstrap/todo-core/config.modules.enterprise.xml"));
        tasks.add(new BootstrapSingleResource("receivers", "just so you can delete stuff easier", "/mgnl-bootstrap/todo-core/config.modules.publishing-core.config.receivers.xml"));
        tasks.add(new BootstrapSingleResource("todofn", "Todo templating functions", "/mgnl-bootstrap/todo-core/config.modules.rendering.renderers.freemarker.contextAttributes.todofn.xml"));
        tasks.add(new BootstrapSingleResource("ticketEndpoint", "Ticket endpoint", "/mgnl-bootstrap/todo-core/rest/config.modules.rest-services.rest-endpoints.xml"));
        tasks.add(new BootstrapSingleResource("website", "Website", "/mgnl-bootstrap-samples/todo-core/website.main.yaml"));
        tasks.add(new BootstrapSingleResource("singleTicket", "ticket", "/mgnl-bootstrap-samples/todo-core/ticket.HIGH_2022-06-27-13-04-30.yaml"));

        return tasks;
    }
}
