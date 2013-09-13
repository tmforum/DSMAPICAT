/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author pierregauthier
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T find(Object id, Set<String> fieldNames) {
        T fullEntity = find(id);
        T viewEntity = getView(fullEntity, fieldNames);
        return viewEntity;
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public void clear() {
        getEntityManager().clear();
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findAllWithFields(Set<String> fieldNames) {
        List<T> list = findAll();
        return getViewList(list, fieldNames);
    }

    public List<T> findByCriteriaWithFields(MultivaluedMap<String, String> map, Set<String> fieldNames, Class<T> clazz) {
        List<T> list = findByCriteria(map, clazz);
        return getViewList(list, fieldNames);
    }

    private List<T> getViewList(List<T> list, Set<String> fieldNames) {
        List<T> resultList = new ArrayList<T>(list.size());
        for (T fullElement : list) {
            T viewElement = getView(fullElement, fieldNames);
            resultList.add(viewElement);
        }
        return resultList;
    }

    protected abstract T getView(T fullElement, Set<String> fieldNames);

    public List<T> findByCriteria(MultivaluedMap<String, String> map, Class<T> clazz) {
        List<T> resultsList = null;
        Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        List<Predicate> andPredicates = new ArrayList<Predicate>();
        Root<T> tt = cq.from(clazz);
        //adding multiple &
        //adding oring 
        //adding greater than 
        //adding regular expression
        //use Map as Entry
        //Predicate predicate = cb.equal(tt.get(name), Severity.valueOf(value));

        while (it.hasNext()) {
            Map.Entry<String, List<String>> sv = it.next();
            System.out.println(sv.getKey());
            System.out.println(sv.getValue());
            if (!sv.getKey().equals("timestamp")) //bug with netbeans test tool
            {
                Predicate predicate = null;
                List<String> valueList = convertMultipleParameters(sv.getValue());
                if (valueList.size() > 1) {
                    List<Predicate> orPredicates = new ArrayList<Predicate>();
                    for (String currentValue : valueList) {
                        Predicate orPredicate = buildPredicate(tt, sv.getKey(), currentValue);
                        orPredicates.add(orPredicate);
                    }
                    predicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                } else {
                    predicate = buildPredicate(tt, sv.getKey(), valueList.get(0));
                }
                andPredicates.add(predicate);
            }
        }


        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()]));
        cq.select(tt);
        TypedQuery<T> q = getEntityManager().createQuery(cq);
        resultsList = q.getResultList();
        return resultsList;

    }

    Predicate buildPredicate(Path<T> tt, String name, Object value) {
        Predicate predicate = null;

        int index = name.indexOf('.');
        boolean isNestedField = index > 0 && index < name.length();
        if (isNestedField) {
            String rootFieldName = name.substring(0, index);
            String subFieldName = name.substring(index + 1);

            Path<T> root = tt.get(rootFieldName);

            predicate = buildPredicate(root, subFieldName, value);

        } else {
            predicate = getEntityManager().getCriteriaBuilder().equal(tt.get(name), value);
        }
        return predicate;
    }

    private static List<String> convertMultipleParameters(List<String> valueList) {
        List<String> newValueList = new ArrayList<String>();
        for (String value : valueList) {
            if (value.startsWith("(") && value.endsWith(")")) {
                String[] tokenArray = value.substring(1, value.length() - 1).split(";");
                newValueList.addAll(Arrays.asList(tokenArray));
            } else {
                newValueList.add(value);
            }
        }
        return newValueList;
    }
}
