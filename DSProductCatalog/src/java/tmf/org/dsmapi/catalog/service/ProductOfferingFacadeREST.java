/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import tmf.org.dsmapi.catalog.Info;
import tmf.org.dsmapi.catalog.ProductOffering;
import tmf.org.dsmapi.catalog.ProductOfferingPrice;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.productoffering")
public class ProductOfferingFacadeREST extends AbstractFacade<ProductOffering> {
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductOfferingFacadeREST() {
        super(ProductOffering.class);
    }

    @POST
    @Override
    @Consumes({ "application/json"})
    public void create(ProductOffering entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(ProductOffering entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({ "application/json"})
    public ProductOffering find(@PathParam("id") String id) {
        return super.find(id);
    }
    
     
    @GET
    @Path("proto")
    @Produces({ "application/json"})
    public ProductOffering proto() {
        ProductOffering po = new ProductOffering();
        Info[] bundledProductOffering = null;
        po.setBundledProductOffering(bundledProductOffering);
        po.setDescription(null);
        po.setId(null);
        po.setIsBundle(Boolean.TRUE);
        po.setName(null);
        Info[] productCategory = null;
        po.setProductCategory(productCategory);
        ProductOfferingPrice[] productOfferingPrice = null;
        po.setProductOfferingPrice(productOfferingPrice);
        po.setProductSpecification(null);
        po.setValidFor(null);
        return po;
    }


    @GET
    @Override
    @Produces({"application/json"})
    public List<ProductOffering> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<ProductOffering> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
