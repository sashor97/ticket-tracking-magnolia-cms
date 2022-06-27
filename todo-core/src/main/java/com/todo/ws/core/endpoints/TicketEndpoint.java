package com.todo.ws.core.endpoints;


import com.google.gson.Gson;
import com.todo.ws.core.model.Ticket;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Path("/tickets")
@Api(value = "/tickets", description = "Alerts API")
public class TicketEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String STATUS_MESSAGE_OK = "OK";
    private static final String STATUS_MESSAGE_BAD_REQUEST = "Request not understood due to errors or malformed syntax";
    private static final String STATUS_MESSAGE_NOT_FOUND = "Not found";
    private static final String STATUS_MESSAGE_ERROR_OCCURRED = "Error occurred";
    protected Gson gson;

    @Inject
    protected TicketEndpoint(D endpointDefinition) {
        super(endpointDefinition);
    }

    @POST
    @Path("/add/ticket")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK),
            @ApiResponse(code = 400, message = STATUS_MESSAGE_BAD_REQUEST),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response addTicket(@ApiParam(value = "Ticket", required = true, name = "ticket") final Ticket ticket) throws RepositoryException {
        final Session jcrSession;
        JsonObject responseObject = new JsonObject();
        try {
            jcrSession = MgnlContext.getJCRSession("ticket");
            final Node root = jcrSession.getRootNode();
            final Node containerNode = JcrUtils.getOrAddNode(root, ticket.getPriority() + "_" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")), "mgnl:Ticket");
            containerNode.setProperty("ticketTitle", ticket.getTitle());
            containerNode.setProperty("ticketDescription", ticket.getDescription());
            containerNode.setProperty("ticketPriority", ticket.getPriority());
            containerNode.setProperty("ticketCompleted", ticket.getCompleted());
            jcrSession.save();
            responseObject.addProperty("success", true);
            return Response.ok(gson.toJson(responseObject)).build();

        } catch (RepositoryException e) {
            log.error("exception " + e);
        }
        responseObject.addProperty("success", false);

        return Response.ok(gson.toJson(responseObject)).build();

    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@QueryParam("uuid") String uuid) throws RepositoryException {

        log.debug("ticket - delete request received.");
        log.debug(String.format("id: %s", uuid));

        if (StringUtils.isEmpty(uuid)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else{
            Session session = MgnlContext.getJCRSession("ticket");
            Node node= NodeUtil.getNodeByIdentifier("ticket",uuid);
            if(node!=null){
                node.remove();
                session.save();
                return Response.ok(Response.Status.OK).build();
            }

        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    @POST
    @Path("/edit/complete/status")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response editTicket(@QueryParam("uuid") String uuid) throws RepositoryException {

        log.debug("ticket - delete request received.");
        log.debug(String.format("id: %s", uuid));

        if (StringUtils.isEmpty(uuid)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else{
            Session session = MgnlContext.getJCRSession("ticket");
            Node node= NodeUtil.getNodeByIdentifier("ticket",uuid);
            if(node!=null){
                node.setProperty("ticketCompleted",!PropertyUtil.getBoolean(node,"ticketCompleted",true));
//                PropertyUtil.setProperty(node,"ticketCompleted",true);
                session.save();
                return Response.ok(Response.Status.OK).build();
            }

        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
