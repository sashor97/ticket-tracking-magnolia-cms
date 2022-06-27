package com.todo.ws.core.setup;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.vpro.jcr.criteria.query.AdvancedResult;
import nl.vpro.jcr.criteria.query.AdvancedResultItem;
import nl.vpro.jcr.criteria.query.Criteria;
import nl.vpro.jcr.criteria.query.JCRCriteriaFactory;
import nl.vpro.jcr.criteria.query.criterion.Criterion;
import nl.vpro.jcr.criteria.query.criterion.Restrictions;
import org.apache.commons.collections.IteratorUtils;
import org.apache.jackrabbit.util.ISO9075;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryUtil {

    private static final Logger log = LoggerFactory.getLogger(QueryUtil.class);

    /**
     * Gets the criteria for any nodeType used as primaryType
     *
     * @param nodeType - string with the nodeType
     * @return {@link Criteria}
     */
    public static Criteria getGenericCriteria(String nodeType) {

        return getGenericCriteria(nodeType, "/");
    }

    public static Criteria getGenericCriteria(String nodeType, String nodePath) {
        return JCRCriteriaFactory.createCriteria()
                .setBasePath(ISO9075.encodePath(nodePath))
                .add(Restrictions.eq(Criterion.JCR_PRIMARYTYPE, "mgnl:" + nodeType));
    }

    /**
     * Gets all nodes by nodeType in a specific workspace.
     *
     * @param workspace - string with the workspace used for {@link QueryUtil#getGenericSession(String)}
     * @param nodeType  - string with the nodeType used for {@link QueryUtil#getGenericCriteria(String)}
     * @return {@link List} of {@link Node}
     */
    public static List<Node> getByNodeType(String workspace, String nodeType) {
        return getByNodeType(workspace, nodeType, false);
    }

    /**
     * Gets all nodes by nodeType in a specific workspace.
     *
     * @param workspace - string with the workspace used for {@link QueryUtil#getGenericSession(String)}
     * @param nodeType  - string with the nodeType used for {@link QueryUtil#getGenericCriteria(String)}
     * @return {@link List} of {@link Node}
     */
    public static List<Node> getByNodeType(String workspace, String nodeType, boolean doInSystemContext) {
        Criteria criteria = QueryUtil.getGenericCriteria(nodeType);
        AdvancedResult ar = criteria.execute(doInSystemContext ? getSystemSession(workspace) : getGenericSession(workspace));

        return advancedResultItemListToNodeList(IteratorUtils.toList(ar.getItems()));
    }

    /**
     * Gets all nodes by nodeType in a specific workspace for parent node.
     *
     * @param workspace - string with the workspace used for {@link QueryUtil#getGenericSession(String)}
     * @param nodeType  - string with the nodeType used for {@link QueryUtil#getGenericCriteria(String)}
     * @param node      - node to get parent path used in  {@link QueryUtil#getGenericCriteria(String)}
     * @return {@link List} of {@link Node}
     */
    public static List<Node> getByNodeType(String workspace, String nodeType, Node node) throws RepositoryException {
        if (node == null){
            return getByNodeType(workspace, nodeType);
        }
        Criteria criteria = QueryUtil.getGenericCriteria(nodeType, node.getPath());
        AdvancedResult ar = criteria.execute(getGenericSession(workspace));

        return advancedResultItemListToNodeList(IteratorUtils.toList(ar.getItems()));
    }

    public static List<Node> getByNodeType(String workspace, String nodeType, List<Node> nodes) throws RepositoryException {
        List<Node> result = new ArrayList<>();
        nodes.forEach(n -> {
            try {
                result.addAll(getByNodeType(workspace, nodeType, n));
            } catch (RepositoryException e) {
                log.error("error getting nodes by type", e);
            }
        });

        return result;
    }

    /**
     * Gets all nodes based on an {@link AdvancedResultItem} search.
     *
     * @param items - {@link List} of {@link AdvancedResultItem}
     * @return {@link List} of {@link Node}
     */
    public static List<Node> advancedResultItemListToNodeList(List<AdvancedResultItem> items) {
        TemplatingFunctions templatingFunctions = Components.getComponent(TemplatingFunctions.class);
        List<Node> result = new LinkedList<>();
        for (AdvancedResultItem advancedResultItem : items) {
            Node node = templatingFunctions.wrapForI18n(advancedResultItem.getJCRNode());
            result.add(node);
        }
        return result;
    }

    /**
     * Gets any workspace session
     *
     * @param sessionName - string with the name of the session
     * @return {@link Session}
     * @throws RepositoryException
     */
    public static Session getGenericSession(String sessionName) {
        try {
            return MgnlContext.getJCRSession(sessionName);
        } catch (RepositoryException e) {
            log.error("can not get " + sessionName + " session");
        }
        return null;
    }

    /**
     * Gets any workspace system session
     *
     * @param sessionName - string with the name of the session
     * @return {@link Session}
     * @throws RepositoryException
     */
    public static Session getSystemSession(String sessionName) {
        try {
            return MgnlContext.getSystemContext().getJCRSession(sessionName);
        } catch (RepositoryException e) {
            log.error("can not get system" + sessionName + " session");
        }
        return null;
    }



}
