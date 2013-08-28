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
import tmf.org.dsmapi.catalog.ProductCategory;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.productcategory")
public class ProductCategoryFacadeREST extends AbstractFacade<ProductCategory> {
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductCategoryFacadeREST() {
        super(ProductCategory.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(ProductCategory entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(ProductCategory entity) {
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
    public ProductCategory find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("proto")
    @Produces({ "application/json"})
    public ProductCategory proto() {
        ProductCategory pc = new ProductCategory();
        pc.setDescription(null);
        pc.setId(null);
        pc.setIsRoot(Boolean.TRUE);
        pc.setName(null);
        pc.setParentId(null);
        return pc;
    }

    @GET
    @Override
    @Produces({ "application/json"})
    public List<ProductCategory> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<ProductCategory> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
