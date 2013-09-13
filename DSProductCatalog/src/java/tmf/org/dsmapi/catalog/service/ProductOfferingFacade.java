/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import java.util.Set;
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

    @Override
    protected ProductOffering getView(ProductOffering fullPO, Set<String> tokens) {
        super.clear();
        ProductOffering resultPO = null;

        if (fullPO != null) {
            
            if (tokens.contains(ProductOfferingAttributesEnum.ALL.getText())) {
                resultPO = fullPO;
                
            } else {
                resultPO = new ProductOffering();
                resultPO.setId(fullPO.getId());
                
                for (String fullFieldName : tokens) {
                    
                    final int index = fullFieldName.indexOf(".");
                    String mainFieldName = null;
                    String subFieldName = null;
                    if (index > 0 && index < fullFieldName.length()){
                        mainFieldName = fullFieldName.substring(0, index);
                        subFieldName = fullFieldName.substring(index+1);
                    } else {
                        mainFieldName = fullFieldName;
                    }
                    
                    ProductOfferingAttributesEnum attribute = ProductOfferingAttributesEnum.fromString(mainFieldName);
                    if (attribute != null) {
                        switch (attribute) {
                            case NAME:
                                resultPO.setName(fullPO.getName());
                                break;
                            case DESCRIPTION:
                                resultPO.setDescription(fullPO.getDescription());
                                break;
                            case IS_BUNDLE:
                            	resultPO.setIsBundle(fullPO.getIsBundle());
                            	break;
                            case VALID_FOR:
                            	resultPO.setValidFor(fullPO.getValidFor());
                            	break;
                            case PRODUCT_CATEGORIES:
                            	resultPO.setProductCategories(fullPO.getProductCategories());
                            	break;
                            case PRODUCT_SPECIFICATION:
                            	resultPO.setProductSpecification(fullPO.getProductSpecification());
                            	break;
                            case PRODUCT_OFFERING_PRICES:
                            	resultPO.setProductOfferingPrices(fullPO.getProductOfferingPrices());
                            	break;
                            case BUNDLED_PRODUCT_OFFERINGS:
                            	resultPO.setBundledProductOfferings(fullPO.getBundledProductOfferings());
                            	break;                        
                       }
                    }
                }
            }
        }

        return resultPO;
    }
    public enum ProductOfferingAttributesEnum {

        ALL("all"),
        ID("id"),
        NAME("name"),
        DESCRIPTION("description"),
        IS_BUNDLE("isBundle"),
        VALID_FOR("validFor"),
        PRODUCT_CATEGORIES("productCategories"),
        PRODUCT_SPECIFICATION("productSpecification"),
        PRODUCT_OFFERING_PRICES("productOfferingPrices"),
        BUNDLED_PRODUCT_OFFERINGS("bundledProductOfferings");

        private String text;

        ProductOfferingAttributesEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static ProductOfferingAttributesEnum fromString(String text) {
            if (text != null) {
                for (ProductOfferingAttributesEnum b : ProductOfferingAttributesEnum.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }
}
