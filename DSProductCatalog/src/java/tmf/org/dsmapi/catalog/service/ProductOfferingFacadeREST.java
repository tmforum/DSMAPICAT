/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.Price;
import tmf.org.dsmapi.catalog.RefInfo;
import tmf.org.dsmapi.catalog.ProductOffering;
import tmf.org.dsmapi.catalog.ProductOfferingPrice;
import tmf.org.dsmapi.catalog.Report;
import tmf.org.dsmapi.catalog.TimeRange;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;
import tmf.org.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("productOffering")
public class ProductOfferingFacadeREST {

    @EJB
    ProductOfferingFacade manager;
    @Context
    UriInfo uriInfo;


    public ProductOfferingFacadeREST() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(ProductOffering entity) {
        manager.create(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response edit(@PathParam("id") String id, ProductOffering entity) {
        Response response = null;
        ProductOffering productOffering = manager.find(id);
        if (productOffering != null) {
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

    @PATCH
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response patch(@PathParam("id") String id, JsonNode root) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ProductOffering partial = mapper.readValue(root, ProductOffering.class);
        partial.setId(id);

        ProductOffering full = manager.find(id);
        if (full != null) {
            FacadeRestUtil.partialUpdate(full, partial, root);
        }

        // 201 OK + location
        UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
        uriBuilder.path("{id}");
        return Response.created(uriBuilder.build(id)).
                entity(full).
                build();


    }

    @GET
    @Produces({"application/json"})
    public Response findByCriteriaWithFields(@Context UriInfo info) throws BadUsageException {
        // search criteria
        MultivaluedMap<String, String> criteria = FacadeRestUtil.parseFields(info);
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        Set<ProductOffering> resultList = findByCriteria(criteria);

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
    private Set<ProductOffering> findByCriteria(MultivaluedMap<String, String> criteria) throws BadUsageException {
        List<ProductOffering> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = manager.findByCriteria(criteria, ProductOffering.class);
        } else {
            resultList = manager.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<ProductOffering>();
        } else {
            return new LinkedHashSet<ProductOffering>(resultList);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") String id, @Context UriInfo info) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(info.getQueryParameters());

        ProductOffering p = manager.find(id);
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

    @DELETE
    @Path("admin/{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }

    @GET
    @Path("admin/count")
    @Produces({"application/json"})
    public Report count() {
        return new Report(manager.count());
    }

    @POST
    @Path("admin")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createList(List<ProductOffering> entities) {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = manager.count();
        int affectedRows;

        affectedRows = manager.create(entities);

        Report stat = new Report(manager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("admin")
    public Report deleteAll() {

        int previousRows = manager.count();
        manager.removeAll();
        int currentRows = manager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @GET
    @Path("proto")
    @Produces({"application/json"})
    public ProductOffering proto() {
        ProductOffering po = new ProductOffering();
        RefInfo[] bundledProductOffering = new RefInfo[1];

        RefInfo refInfo = new RefInfo();
        refInfo.setDescription("desc");
        refInfo.setId("id");
        refInfo.setName("name");
        bundledProductOffering[0] = refInfo;

        po.setBundledProductOfferings(Arrays.asList(bundledProductOffering));
        po.setDescription("ProductOffering");
        po.setId("id");
        po.setIsBundle(Boolean.TRUE);
        po.setName("PO");

        RefInfo[] productCategory = new RefInfo[2];
        productCategory[0] = refInfo;
        productCategory[1] = refInfo;

        TimeRange vf = new TimeRange();
        vf.setEndDateTime(new Date());
        vf.setStartDateTime(new Date());

        po.setProductCategories(Arrays.asList(productCategory));

        ProductOfferingPrice[] productOfferingPrices = new ProductOfferingPrice[1];
        ProductOfferingPrice pop = new ProductOfferingPrice();
        pop.setName("name");
        Price price = new Price();
        price.setAmount("amount");
        price.setCurrency("currency");

        pop.setPrice(price);

        pop.setRecurringChargePeriod("period");
        pop.setUnitOfMeasure(null);
        pop.setValidFor(vf);

        productOfferingPrices[0] = pop;
        po.setProductOfferingPrices(Arrays.asList(productOfferingPrices));

        po.setProductSpecification(refInfo);

        po.setValidFor(vf);

        return po;
    }
}
