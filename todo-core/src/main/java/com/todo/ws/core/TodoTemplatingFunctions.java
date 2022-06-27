package com.todo.ws.core;

import com.google.common.collect.ImmutableMap;
import com.todo.ws.core.setup.QueryUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.jcr.predicate.NodeTypePredicate;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.link.LinkUtil;
import info.magnolia.module.site.SiteManager;
import info.magnolia.objectfactory.Components;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.vpro.jcr.criteria.query.AdvancedResult;
import nl.vpro.jcr.criteria.query.Criteria;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.util.BasicAuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.text.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.util.Calendar.getInstance;

public class TodoTemplatingFunctions {
    private static final Logger log = LoggerFactory.getLogger(TodoTemplatingFunctions.class);
    private final TemplatingFunctions cmsfn;
    private final DamTemplatingFunctions damfn;
    @Inject
    public TodoTemplatingFunctions(TemplatingFunctions cmsfn, DamTemplatingFunctions damfn) {
        this.cmsfn = cmsfn;
        this.damfn = damfn;
    }
    public static Node getNodeByDepth(Node node, int depth) throws RepositoryException {
        Node current = node;
        while (current.getDepth() != depth) {
            current = current.getParent();
        }
        return current;
    }
    public static String addParamReturnUrl(String param, String paramValue, boolean bool) {

        String queryString = MgnlContext.getWebContext().getRequest().getQueryString();
        return addParamReturnUrl(param, paramValue, bool, queryString);
    }
    public static String addParamReturnUrl(String param, String paramValue, boolean bool, String url) {
        if (bool) {
            String queryString = url;
            Map<String, String> map = new HashMap<>();
            StringBuilder result = new StringBuilder();
            if (StringUtils.isNotBlank(queryString)) {
                String[] splitQS = queryString.split("&");
                for (String s : splitQS) {
                    String[] split = s.split("=");
                    if (split.length == 2) {
                        map.put(split[0], split[1]);
                    }
                }
                map.forEach((k, v) -> {
                    if (k.equals(param)) {
                        result.append(k + "=" + paramValue + "&");
                    } else {
                        result.append(k + "=" + v + "&");
                    }
                });
            }
            if (StringUtils.isNotBlank(param) && StringUtils.isNotBlank(paramValue)) {
                if (!map.containsKey(param)) {
                    result.append(param + "=" + paramValue);
                }
            }

            String finalResult = result.toString();
            if (finalResult.endsWith("&")) finalResult = finalResult.substring(0, finalResult.length() - 1);
            return finalResult;
        } else {
            return param + "=" + paramValue;
        }
    }

    public static String addParamReturnUrl(boolean bool) {
        return addParamReturnUrl("", "", bool);
    }

    public static boolean hasParam(String paramName) {
        String queryString = MgnlContext.getWebContext().getRequest().getQueryString();
        return queryString != null && queryString.contains(paramName);
    }

    public Map<String, List<ContentMap>> navItems(ContentMap page) throws RepositoryException {

        if (page == null) {
            return ImmutableMap.of(StringUtils.EMPTY, Collections.emptyList());
        }

        List<ContentMap> navigationItems = cmsfn.asContentMapList(
                NodeUtil.asList(NodeUtil.getNodes(page.getJCRNode(),
                        new NodeTypePredicate("mgnl:page", false))));

        if (page.containsKey("orderItemsAlphabetically")) {
            if (Boolean.parseBoolean(page.get("orderItemsAlphabetically").toString())) {
                Collator coll = Collator.getInstance(MgnlContext.getLocale());
                coll.setStrength(Collator.PRIMARY);
                navigationItems.sort(
                        Comparator.comparing(p -> p.get("title") != null ? p.get("title").toString() : "", coll));
            }
        }

        Map<String, List<ContentMap>> groupedNavigationItems = navigationItems.stream().collect(
                Collectors.groupingBy(c -> c.get("menuCategory") == null ? "" : (String) c.get("menuCategory")));

        return groupedNavigationItems;
    }
    public String getLink(String type) {

        try {
            String id = MgnlContext.getParameter(type);
            Node node = NodeUtil.getNodeByIdentifier("website", id);
            return node.getPath();
        } catch (RepositoryException e) {
            log.error("error creating link", e);
        }

        return StringUtils.EMPTY;
    }

    public static Node getNodeByIdentifier(String workspace, String uuid) {
        try {
            return NodeUtil.getNodeByIdentifier(workspace, uuid);
        } catch (RepositoryException e) {
            log.error("error getting node by uuid", e);
        }

        return null;
    }
    public List<Node> getTicketNodes(){
        Session notificationsSession = QueryUtil.getGenericSession("ticket");
        Criteria notificationsCriteria = QueryUtil.getGenericCriteria("Ticket");
        AdvancedResult ar = notificationsCriteria.execute(notificationsSession);
        List<Node> ticketNodes = QueryUtil.advancedResultItemListToNodeList(IteratorUtils.toList(ar.getItems()));
        return ticketNodes;
    }

    public static <T> void test(T t) {
        System.out.println(t);
    }
}
