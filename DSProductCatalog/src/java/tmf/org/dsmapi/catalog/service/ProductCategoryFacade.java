/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ProductCategory;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class ProductCategoryFacade extends AbstractFacade<ProductCategory> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductCategoryFacade() {
        super(ProductCategory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
