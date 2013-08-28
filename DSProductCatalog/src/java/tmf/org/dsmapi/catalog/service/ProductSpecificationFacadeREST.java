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
public class ProductSpecificationFacadeREST extends AbstractFacade<ProductSpecification> {
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductSpecificationFacadeREST() {
        super(ProductSpecification.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(ProductSpecification entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({ "application/json"})
    public void edit(ProductSpecification entity) {
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
    public ProductSpecification find(@PathParam("id") String id) {
        return super.find(id);
    }
    
      
    @GET
    @Path("proto")
    @Produces({ "application/json"})
    public ProductSpecification proto() {
        ProductSpecification ps = new ProductSpecification();
        ps.setId("id");
        ps.setBrand("brand");
        ps.setDescription("description");
        ps.setName("name");
        TimeRange vfor = new TimeRange();
        vfor.setEndDateTime("endtime");
        vfor.setStartDateTime("startime");
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
        
        ProductSpecCharacteristic[] productSpecCharacteristics =new ProductSpecCharacteristic[1];
        productSpecCharacteristics[0] = psc;
        ps.setProductSpecCharacteristics(productSpecCharacteristics);
        
        
        return ps;
    }


    @GET
    @Override
    @Produces({ "application/json"})
    public List<ProductSpecification> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<ProductSpecification> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
