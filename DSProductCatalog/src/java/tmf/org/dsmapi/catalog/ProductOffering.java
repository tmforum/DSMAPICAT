/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * {"productOffering": {
    "id": “http://serverlocation:port/catalogManagement/productOffering/42",
    "name": "Virtual Storage Medium",
    "description": "Virtual Storage Medium",
    "isBundle": "false",
    "productCategories": [{
        "id": “http://serverlocation:port/catalogManagement/productCategory/12",
        "name": "Cloud offerings",
        "description": "A group of cloud service offerings"
    }],
    "validFor": {
        "startDateTime": "2013-04-19T16:42:23-04:00",
        "endDateTime": "2013-06-19T00:00:00-04:00"
    },
    "productSpecification": {
        "id": “http://serverlocation:port/catalogManagement/productSpecification/13",
        "name": "specification 1",
        "description": "description 1"
    },
    "productOfferingPrice": [
        {
            "name": "Monthly Price",
            "description": "monthlyprice",
            "validFor": {
                "startDateTime": "2013-04-19T16:42:23-04:00",
                "endDateTime": "2013-06-19T00:00:00-04:00"
            },
            "priceType": "recurring",
            "unitOfMeasure": "",
            "price": {
                "amount": "12",
                "currency": "$"
            },
            "recurringChargePeriod": "monthly"
        },
        {
            "name": "Usage Price",
            "description": "usageprice",
            "validFor": {
                "startDateTime": "2013-04-19T16:42:23-04:00",
                "endDateTime": "2013-06-19T00:00:00-04:00"
            },
            "priceType": "usage",
            "unitOfMeasure": "second",
            "price": {
                "amount": "12",
                "currency": "$"
            },
            "recurringChargePeriod": ""
        }
    ],
    "bundledProductOfferings": {"productOffering": [
        "id": “http://serverlocation:port/catalogManagement/productOffering/15",
        "name": "Offering 1",
        "description": "description 1"
    ]}
}}
 * @author pierregauthier
 */
@Entity
@XmlRootElement
public class ProductOffering implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    public String getId() {
        return id;
    }

    public RefInfo[] getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(RefInfo[] productCategories) {
        this.productCategories = productCategories;
    }

    public ProductOfferingPrice[] getProductOfferingPrices() {
        return productOfferingPrices;
    }

    public void setProductOfferingPrices(ProductOfferingPrice[] productOfferingPrices) {
        this.productOfferingPrices = productOfferingPrices;
    }

    public RefInfo[] getBundledProductOfferings() {
        return bundledProductOfferings;
    }

    public void setBundledProductOfferings(RefInfo[] bundledProductOfferings) {
        this.bundledProductOfferings = bundledProductOfferings;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    String name;
    
    String description;
    
    Boolean isBundle;
    
    TimeRange validFor;
    
    RefInfo[] productCategories;
    
    RefInfo productSpecification; 
    
    ProductOfferingPrice[] productOfferingPrices;
    
    RefInfo[] bundledProductOfferings;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    public void setProductCategory(RefInfo[] productCategories) {
        this.productCategories = productCategories;
    }

    public void setProductSpecification(RefInfo productSpecification) {
        this.productSpecification = productSpecification;
    }

    public void setProductOfferingPrice(ProductOfferingPrice[] productOfferingPrices) {
        this.productOfferingPrices = productOfferingPrices;
    }

    public void setBundledProductOffering(RefInfo[] bundledProductOfferings) {
        this.bundledProductOfferings = bundledProductOfferings;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public TimeRange getValidFor() {
        return validFor;
    }


    public ProductOfferingPrice[] getProductOfferingPrice() {
        return productOfferingPrices;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductOffering other = (ProductOffering) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.isBundle != other.isBundle && (this.isBundle == null || !this.isBundle.equals(other.isBundle))) {
            return false;
        }
        if (this.validFor != other.validFor && (this.validFor == null || !this.validFor.equals(other.validFor))) {
            return false;
        }
        if (!Arrays.deepEquals(this.productCategories, other.productCategories)) {
            return false;
        }
        if (this.productSpecification != other.productSpecification && (this.productSpecification == null || !this.productSpecification.equals(other.productSpecification))) {
            return false;
        }
        if (!Arrays.deepEquals(this.productOfferingPrices, other.productOfferingPrices)) {
            return false;
        }
        if (!Arrays.deepEquals(this.bundledProductOfferings, other.bundledProductOfferings)) {
            return false;
        }
        return true;
    }
  

   

   
    

    @Override
    public String toString() {
        return "ProductOffering{" + "id=" + id + ", name=" + name + ", description=" + description + ", isBundle=" + isBundle + ", validFor=" + validFor + ", productCategories=" + productCategories + ", productSpecification=" + productSpecification + ", productOfferingPrices=" + productOfferingPrices + ", bundledProductOffering=" + bundledProductOfferings + '}';
    }

   
    
}
