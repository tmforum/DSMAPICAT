/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

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

}
