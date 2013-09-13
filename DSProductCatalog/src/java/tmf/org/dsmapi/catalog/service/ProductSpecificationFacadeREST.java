/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.ProductSpecCharacteristic;
import tmf.org.dsmapi.catalog.ProductSpecCharacteristicValue;
import tmf.org.dsmapi.catalog.ProductSpecification;
import tmf.org.dsmapi.catalog.TimeRange;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.productspecification")
public class ProductSpecificationFacadeREST {

    @EJB
    private ProductSpecificationFacade manager;

    public ProductSpecificationFacadeREST() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(ProductSpecification entity) {
        entity.setId(null);
        manager.create(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @PUT
    @Consumes({"application/json"})
    public Response edit(ProductSpecification entity) {
        Response response = null;
        ProductSpecification productSpecification = manager.find(entity.getId());
        if (productSpecification != null) {
            // 200
            manager.edit(entity);
            response = Response.ok(entity).build();
        } else {
            // 404 not found
            response = Response.status(404).build();
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }

    @GET
    @Produces({"application/json"})
    public Response findByCriteriaWithFields(@Context UriInfo info) {

        // search criteria
        MultivaluedMap<String, String> criteria = info.getQueryParameters();
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        List<ProductSpecification> resultList = findByCriteria(criteria);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(FacadeRestUtil.ID_FIELD);
            List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
            for (ProductSpecification productOffering : resultList) {
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(productOffering, fieldSet);
                nodeList.add(node);
            }
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    private List<ProductSpecification> findByCriteria(MultivaluedMap<String, String> criteria) {
        List<ProductSpecification> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = manager.findByCriteria(criteria, ProductSpecification.class);
        } else {
            resultList = manager.findAll();
        }
        return resultList;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findWithFields(@PathParam("id") String id, @Context UriInfo info) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(info.getQueryParameters());

        ProductSpecification p = manager.find(id);
        Response response;
        // if troubleTicket exists
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
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(manager.count());
    }

    @GET
    @Path("proto")
    @Produces({"application/json"})
    public ProductSpecification proto() {
        ProductSpecification ps = new ProductSpecification();
        ps.setId("id");
        ps.setBrand("brand");
        ps.setDescription("description");
        ps.setName("name");
        TimeRange vfor = new TimeRange();
        vfor.setEndDateTime(new Date());
        vfor.setStartDateTime(new Date());
        ps.setValidFor(vfor);

        ProductSpecCharacteristic psc = new ProductSpecCharacteristic();
        psc.setConfigurable(Boolean.TRUE);
        psc.setDescription("description");
        psc.setName("name");


        ProductSpecCharacteristicValue pscv = new ProductSpecCharacteristicValue();
        pscv.setDefaultValue(Boolean.TRUE);
        pscv.setValueFrom("valueFrom");
        pscv.setValueFrom("valueTo");
        pscv.setUnitOfMeasure("unit");
        pscv.setValue("value");
        pscv.setValueType("valueType");

        ProductSpecCharacteristicValue[] productSpecCharacteristicValues = new ProductSpecCharacteristicValue[1];
        productSpecCharacteristicValues[0] = pscv;

        psc.setProductSpecCharacteristicValues(productSpecCharacteristicValues);

        ProductSpecCharacteristic[] productSpecCharacteristics = new ProductSpecCharacteristic[1];
        productSpecCharacteristics[0] = psc;
        ps.setProductSpecCharacteristics(productSpecCharacteristics);


        return ps;
    }
}
