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
import tmf.org.dsmapi.catalog.Price;
import tmf.org.dsmapi.catalog.RefInfo;
import tmf.org.dsmapi.catalog.ProductOffering;
import tmf.org.dsmapi.catalog.ProductOfferingPrice;
import tmf.org.dsmapi.catalog.TimeRange;

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
        RefInfo[] bundledProductOffering = new RefInfo[1];
        
        RefInfo refInfo = new RefInfo();
        refInfo.setDescription("desc");
        refInfo.setHref("href");
        refInfo.setName("name");
        bundledProductOffering[0]= refInfo;
        
        po.setBundledProductOffering(bundledProductOffering);
        po.setDescription("desc");
        po.setId("id");
        po.setIsBundle(Boolean.TRUE);
        po.setName("name");
        
        RefInfo[] productCategory = new RefInfo[1];
        productCategory[0]= refInfo;
        
        TimeRange vf = new TimeRange();
        vf.setEndDateTime("endDateTime");
        vf.setStartDateTime("startDateTime");
        
        po.setProductCategory(productCategory);
        ProductOfferingPrice[] productOfferingPrices = new ProductOfferingPrice[1] ;
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
        po.setProductOfferingPrice(productOfferingPrices);
       
        po.setProductSpecification(refInfo);
        
        po.setValidFor(vf);
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
