/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.Set;
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

    @Override
    protected ProductCategory getView(ProductCategory fullPC, Set<String> fieldNames) {

        ProductCategory resultPC = null;

        if (fullPC != null) {

            if (fieldNames.contains(ProductCategoryAttributesEnum.ALL.getText())) {
                resultPC = fullPC;

            } else {
                resultPC = new ProductCategory();
                resultPC.setId(fullPC.getId());

                for (String fieldName : fieldNames) {
                    ProductCategoryAttributesEnum attribute = ProductCategoryAttributesEnum.fromString(fieldName);
                    if (attribute != null) {
                        switch (attribute) {
                            case PARENT_H_REF:
                                resultPC.setParentHRef(fullPC.getParentHRef());
                                break;
                            case IS_ROOT:
                                resultPC.setIsRoot(fullPC.getIsRoot());
                                break;
                            case NAME:
                                resultPC.setName(fullPC.getName());
                                break;
                            case DESCRIPTION:
                                resultPC.setDescription(fullPC.getDescription());
                                break;
                        }
                    }
                }
            }
        }

        return resultPC;
    }

    public enum ProductCategoryAttributesEnum {

        ALL("all"),
        ID("id"),
        PARENT_H_REF("parentHRef"),
        IS_ROOT("isRoot"),
        NAME("name"),
        DESCRIPTION("description");
        private String text;

        ProductCategoryAttributesEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static ProductCategoryAttributesEnum fromString(String text) {
            if (text != null) {
                for (ProductCategoryAttributesEnum b : ProductCategoryAttributesEnum.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }
}
