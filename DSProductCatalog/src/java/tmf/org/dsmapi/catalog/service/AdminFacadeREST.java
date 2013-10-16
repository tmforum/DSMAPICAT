/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import tmf.org.dsmapi.catalog.ProductCategory;
import tmf.org.dsmapi.catalog.ProductOffering;
import tmf.org.dsmapi.catalog.ProductSpecification;
import tmf.org.dsmapi.catalog.Report;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("admin")
public class AdminFacadeREST {

    @EJB
    ProductCategoryFacade productCategoryManager;
    @EJB
    ProductOfferingFacade productOfferingManager;
    @EJB
    private ProductSpecificationFacade productSpecificationManager;

    public AdminFacadeREST() {
    }

    @DELETE
    @Path("productCategory/{id}")
    public void removeCategory(@PathParam("id") String id) {
        productCategoryManager.remove(productCategoryManager.find(id));
    }

    @GET
    @Path("productCategory/count")
    @Produces({"application/json"})
    public Report countCategory() {
        return new Report(productCategoryManager.count());
    }

    @POST
    @Path("productCategory")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createListCategory(List<ProductCategory> entities) {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = productCategoryManager.count();
        int affectedRows;

        affectedRows = productCategoryManager.create(entities);

        Report stat = new Report(productCategoryManager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("productCategory")
    public Report deleteAllCategory() {

        int previousRows = productCategoryManager.count();
        productCategoryManager.removeAll();
        int currentRows = productCategoryManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("productOffering/{id}")
    public void removeOffering(@PathParam("id") String id) {
        productOfferingManager.remove(productOfferingManager.find(id));
    }

    @GET
    @Path("productOffering/count")
    @Produces({"application/json"})
    public Report countOffering() {
        return new Report(productOfferingManager.count());
    }

    @POST
    @Path("productOffering")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createListOffering(List<ProductOffering> entities) {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = productOfferingManager.count();
        int affectedRows;

        affectedRows = productOfferingManager.create(entities);

        Report stat = new Report(productOfferingManager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("productOffering")
    public Report deleteAllOffering() {

        int previousRows = productOfferingManager.count();
        productOfferingManager.removeAll();
        int currentRows = productOfferingManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("productSpecification/{id}")
    public void removeSpecification(@PathParam("id") String id) {
        productSpecificationManager.remove(productSpecificationManager.find(id));
    }

    @GET
    @Path("productSpecification/count")
    @Produces({"application/json"})
    public Report countSpecification() {
        return new Report(productSpecificationManager.count());
    }

    @POST
    @Path("productSpecification")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createListSpecification(List<ProductSpecification> entities) {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = productSpecificationManager.count();
        int affectedRows;

        affectedRows = productSpecificationManager.create(entities);

        Report stat = new Report(productSpecificationManager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("productSpecification")
    public Report deleteAllSpecification() {

        int previousRows = productSpecificationManager.count();
        productSpecificationManager.removeAll();
        int currentRows = productSpecificationManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }
}
