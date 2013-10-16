/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.ProductCategory;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("productCategory")
public class ProductCategoryFacadeREST {

    @EJB
    ProductCategoryFacade manager;

    public ProductCategoryFacadeREST() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(ProductCategory entity) {
        manager.create(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response edit(@PathParam("id") String id, ProductCategory entity) {
        Response response = null;
        ProductCategory productCategory = manager.find(id);
        if (productCategory != null) {
            // 200
            entity.setId(id);
            manager.edit(entity);
            response = Response.ok(entity).build();
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Produces({"application/json"})
    public Response findByCriteriaWithFields(@Context UriInfo info) {
        // search criteria
        MultivaluedMap<String, String> criteria = FacadeRestUtil.parseFields(info);
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        Set<ProductCategory> resultList = findByCriteria(criteria);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(FacadeRestUtil.ID_FIELD);
            List<ObjectNode> nodeList = FacadeRestUtil.createNodeListViewWithFields(resultList, fieldSet);
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    // return Set of unique elements to avoid List with same elements in case of join
    private Set<ProductCategory> findByCriteria(MultivaluedMap<String, String> criteria) {
        List<ProductCategory> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = manager.findByCriteria(criteria, ProductCategory.class);
        } else {
            resultList = manager.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<ProductCategory>();
        } else {
            return new LinkedHashSet<ProductCategory>(resultList);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") String id, @Context UriInfo info) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(info.getQueryParameters());

        ProductCategory p = manager.find(id);
        Response response;
        if (p != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
                response = Response.ok(p).build();
            } else {
                fieldSet.add(FacadeRestUtil.ID_FIELD);
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(p, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Path("proto")
    @Produces({"application/json"})
    public ProductCategory proto() {
        ProductCategory pc = new ProductCategory();
        pc.setDescription("description");
        pc.setId("id");
        pc.setIsRoot(Boolean.TRUE);
        pc.setName("name");

        return pc;
    }
}
