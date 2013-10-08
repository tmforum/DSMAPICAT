/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 *
 * @author jyus7291
 */
public class FacadeRestUtil {

    private static PropertyUtilsBean pub = new PropertyUtilsBean();
    public static final String ALL_FIELDS = "all";
    public static final String ID_FIELD = "id";
    public static final String QUERY_KEY_FIELD = "fields";
    public static final String QUERY_KEY_FIELD_ESCAPE = ":";

    public static ObjectNode createNodeViewWithFields(Object bean, Set<String> fieldNames) {
        ObjectMapper mapper = new ObjectMapper();
        return createNodeViewWithFields(mapper, bean, fieldNames);
    }

    public static List<ObjectNode> createNodeListViewWithFields(Collection list, Set<String> fieldNames) {
        List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
        for (Object element : list) {
            ObjectNode node = FacadeRestUtil.createNodeViewWithFields(element, fieldNames);
            nodeList.add(node);
        }
        return nodeList;
    }

    private static ObjectNode createNodeViewWithFields(ObjectMapper mapper, Object bean, Set<String> fieldNames) {
        // split fieldNames in 2 categories : 
        // simpleFields for simple property names with no '.'
        // nestedFields for nested property names with a '.'
        Set<String> simpleFields = new HashSet<String>();
        MultivaluedMapImpl nestedFields = new MultivaluedMapImpl();
        for (String fieldName : fieldNames) {
            int index = fieldName.indexOf('.');
            boolean isNestedField = index > 0 && index < fieldName.length();
            if (isNestedField) {
                String rootFieldName = fieldName.substring(0, index);
                String subFieldName = fieldName.substring(index + 1);
                nestedFields.add(rootFieldName, subFieldName);
            } else {
                simpleFields.add(fieldName);
            }
        }

        // create a simple node with only one level containing all simples properties
        ObjectNode rootNode = createNodeWithSimpleFields(mapper, bean, simpleFields);

        // create nested nodes with deeper levels
        Set<Map.Entry<String, List<String>>> entrySet = nestedFields.entrySet();
        // for each nested property, create recursively a node        
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String rootFieldName = entry.getKey();
            // add in current node only if full property is not already present in 1st level
            if (!simpleFields.contains(rootFieldName)) {
                Object nestedBean = getField(bean, rootFieldName);
                // add only non null fields
                if (nestedBean == null) {
                    break;
                }
                Set<String> nestedFieldNames = new HashSet<String>(entry.getValue());
                // current node is an array or a list
                if ((nestedBean.getClass().isArray()) || (Collection.class.isAssignableFrom(nestedBean.getClass()))) {
                    Object[] array = null;
                    if ((nestedBean.getClass().isArray())) {
                        array = (Object[]) nestedBean;
                    } else {
                        Collection collection = (Collection) nestedBean;
                        array = collection.toArray();
                    }
                    if (array.length > 0) {
                        // create a node for each element in array 
                        // and add created node in an arrayNode
                        Collection<JsonNode> nodes = new LinkedList<JsonNode>();
                        for (Object object : array) {
                            ObjectNode nestedNode = createNodeViewWithFields(mapper, object, nestedFieldNames);
                            if (nestedNode != null && nestedNode.size() > 0) {
                                nodes.add(nestedNode);
                            }
                        }
                        ArrayNode arrayNode = mapper.createArrayNode();
                        arrayNode.addAll(nodes);
                        if (arrayNode.size() > 0) {
                            rootNode.put(rootFieldName, arrayNode);
                        }
                    }
                } else {
                    // create recursively a node and add it in current root node
                    ObjectNode nestedNode = createNodeViewWithFields(mapper, nestedBean, nestedFieldNames);
                    if (nestedNode != null && nestedNode.size() > 0) {
                        rootNode.put(rootFieldName, nestedNode);
                    }
                }
            }
        }
        return rootNode;
    }

    // create a simple flat node with only one-level fields
    private static ObjectNode createNodeWithSimpleFields(ObjectMapper mapper, Object bean, Set<String> fieldNames) {
        ObjectNode node = mapper.createObjectNode();
        for (String fieldName : fieldNames) {
            Object fieldValue = getField(bean, fieldName);
            if (fieldValue != null) {
                nodePut(node, fieldName, fieldValue);
            }
        }
        return node;
    }

    // generic node.put for any Object
    private static void nodePut(ObjectNode node, String fieldName, Object value) {
        if (value instanceof Boolean) {
            node.put(fieldName, (Boolean) value);
        } else if (value instanceof Integer) {
            node.put(fieldName, (Integer) value);
        } else if (value instanceof Long) {
            node.put(fieldName, (Long) value);
        } else if (value instanceof Float) {
            node.put(fieldName, (Float) value);
        } else if (value instanceof Double) {
            node.put(fieldName, (Double) value);
        } else if (value instanceof BigDecimal) {
            node.put(fieldName, (BigDecimal) value);
        } else if (value instanceof String) {
            node.put(fieldName, (String) value);
        } else {
            node.putPOJO(fieldName, value);
        }

    }

    // get value of field named "name" in bean
    public static Object getField(Object bean, String name) {
        try {
            return pub.getNestedProperty(bean, name);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setField(Object bean, String name, Object value) {
        try {
            pub.setNestedProperty(bean, name, value);
        } catch (Exception ex) {
        }
    }

    public static void partialUpdate(Object current, Object partial, JsonNode root) {
        Iterator<String> it = root.getFieldNames();
        while (it.hasNext()) {
            String field = it.next();
            JsonNode child = root.get(field);
            Object partialValue = FacadeRestUtil.getField(partial, field);
            if (child.isObject()) {
                Object currentValue = FacadeRestUtil.getField(current, field);
                if (currentValue != null) {
                    partialUpdate(currentValue, partialValue, child);
                    FacadeRestUtil.setField(current, field, currentValue);
                } else {
                    FacadeRestUtil.setField(current, field, partialValue);
                }
            } else {
                FacadeRestUtil.setField(current, field, partialValue);
            }
        }
    }

    public static Set<String> getFieldSet(MultivaluedMap<String, String> criteria) {
        Set<String> fieldSet = new HashSet<String>();
        if (criteria != null) {
            List<String> queryParameterField = criteria.remove(QUERY_KEY_FIELD_ESCAPE + QUERY_KEY_FIELD);
            if (queryParameterField == null) {
                queryParameterField = criteria.remove(QUERY_KEY_FIELD);
            }
            if (queryParameterField != null && !queryParameterField.isEmpty()) {
                String queryParameterValue = queryParameterField.get(0);
                if (queryParameterValue != null) {
                    String[] tokenArray = queryParameterValue.split(",");
                    fieldSet.addAll(Arrays.asList(tokenArray));
                }
            }
        }
        return fieldSet;
    }

    public static MultivaluedMap<String, String> parseFields(UriInfo info) {
        //return info.getQueryParameters();
        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
        try {
            URI requestUri = info.getRequestUri();
            String rawQuery = requestUri.getRawQuery();
            if (rawQuery == null || rawQuery.length() == 0) {
                return map;
            }
            String query = URLDecoder.decode(rawQuery, "UTF-8");
            if (query == null || query.length() == 0) {
                return map;
            }

            StringBuffer attribute = new StringBuffer();
            StringBuffer value = new StringBuffer();
            boolean inAttr = true;
            boolean inValue = false;
            int brackets = 0;
            for (int i = 0; i < query.length(); i++) {
                char c = query.charAt(i);
                switch (c) {
                    case '&':
                        if (brackets <= 0) {
                            brackets = 0;
                            if (attribute.length() > 0) {
                                map.putSingle(attribute.toString(), value.toString());
                            }
                            attribute = new StringBuffer();
                            value = new StringBuffer();
                            inAttr = true;
                            inValue = false;
                        } else {
                            if (inValue) {
                                value.append(c);
                            } else if (inAttr) {
                                attribute.append(c);
                            }
                        }
                        break;
                    case '=':
                        if (brackets <= 0) {
                            brackets = 0;
                            inValue = true;
                            inAttr = false;
                            value = new StringBuffer();
                        } else {
                            if (inValue) {
                                value.append(c);
                            } else if (inAttr) {
                                attribute.append(c);
                            }
                        }
                        break;
                    case '(':
                        if (inValue) {
                            brackets++;
                            value.append(c);
                        } else if (inAttr) {
                            attribute.append(c);
                        }
                        break;
                    case ')':
                        if (inValue) {
                            brackets--;
                            value.append(c);
                        } else if (inAttr) {
                            attribute.append(c);
                        }
                        break;

                    default:
                        if (inValue) {
                            value.append(c);
                        } else if (inAttr) {
                            attribute.append(c);
                        }
                        break;
                }
            }
            if (attribute.length() > 0) {
                map.putSingle(attribute.toString(), value.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FacadeRestUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;

    }
}
