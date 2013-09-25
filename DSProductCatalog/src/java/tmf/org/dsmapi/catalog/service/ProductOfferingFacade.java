/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ProductOffering;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class ProductOfferingFacade extends AbstractFacade<ProductOffering> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductOfferingFacade() {
        super(ProductOffering.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(ProductOffering entity) {
        super.create(entity);
    }

    public void removeAll() {
        //DELETE FROM PO_BUNDLED_PO_REF WHERE (PRODUCT_OFFERING_ID = ?)
        //DELETE FROM PO_PRODUCT_CATEGORY_REF WHERE (PRODUCT_OFFERING_ID = ?)
        //DELETE FROM PO_PRODUCT_OFFERING_PRICE WHERE (PRODUCT_OFFERING_ID = ?)
        //DELETE FROM PRODUCTOFFERING WHERE (ID = ?)
        List<ProductOffering> all = findAll();
        for (ProductOffering productOffering : all) {
            remove(productOffering);
        }
    }
}
