/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ProductSpecification;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class ProductSpecificationFacade extends AbstractFacade<ProductSpecification> {
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;

    public ProductSpecificationFacade() {
        super(ProductSpecification.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected ProductSpecification getView(ProductSpecification fullPS, Set<String> tokens) {

        ProductSpecification resultPS = null;

        if (fullPS != null) {

            if (tokens.contains(ProductSpecificationAttributesEnum.ALL.getText())) {
                resultPS = fullPS;

            } else {
                resultPS = new ProductSpecification();
                resultPS.setId(fullPS.getId());

                for (String token : tokens) {
                    ProductSpecificationAttributesEnum attribute = ProductSpecificationAttributesEnum.fromString(token);
                    if (attribute != null) {
                        switch (attribute) {
                            case NAME:
                                resultPS.setName(fullPS.getName());
                                break;
                            case DESCRIPTION:
                                resultPS.setDescription(fullPS.getDescription());
                                break;
                            case BRAND:
                                resultPS.setBrand(fullPS.getBrand());
                                break;
                            case VALID_FOR:
                            	resultPS.setValidFor(fullPS.getValidFor());
                            	break;
                            case PRODUCT_SPEC_CHARACTERISTICS:
                            	resultPS.setProductSpecCharacteristics(fullPS.getProductSpecCharacteristics());
                            	break;
                        }
                    }
                }
            }
        }

        return resultPS;
    }

    public enum ProductSpecificationAttributesEnum {

        ALL("all"),
        ID("id"),
        NAME("name"),
        DESCRIPTION("description"),
        BRAND("brand"),
        VALID_FOR("validFor"),
        PRODUCT_SPEC_CHARACTERISTICS("productSpecCharacteristics");
        private String text;

        ProductSpecificationAttributesEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static ProductSpecificationAttributesEnum fromString(String text) {
            if (text != null) {
                for (ProductSpecificationAttributesEnum b : ProductSpecificationAttributesEnum.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }
    
}
