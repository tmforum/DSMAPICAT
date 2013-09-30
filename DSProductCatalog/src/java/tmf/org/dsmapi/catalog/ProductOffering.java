/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * {"productOffering": { "id":
 * “http://serverlocation:port/catalogManagement/productOffering/42", "name":
 * "Virtual Storage Medium", "description": "Virtual Storage Medium",
 * "isBundle": "false", "productCategories": [{ "id":
 * “http://serverlocation:port/catalogManagement/productCategory/12", "name":
 * "Cloud offerings", "description": "A group of cloud service offerings" }],
 * "validFor": { "startDateTime": "2013-04-19T16:42:23-04:00", "endDateTime":
 * "2013-06-19T00:00:00-04:00" }, "productSpecification": { "id":
 * “http://serverlocation:port/catalogManagement/productSpecification/13",
 * "name": "specification 1", "description": "description 1" },
 * "productOfferingPrice": [ { "name": "Monthly Price", "description":
 * "monthlyprice", "validFor": { "startDateTime": "2013-04-19T16:42:23-04:00",
 * "endDateTime": "2013-06-19T00:00:00-04:00" }, "priceType": "recurring",
 * "unitOfMeasure": "", "price": { "amount": "12", "currency": "$" },
 * "recurringChargePeriod": "monthly" }, { "name": "Usage Price", "description":
 * "usageprice", "validFor": { "startDateTime": "2013-04-19T16:42:23-04:00",
 * "endDateTime": "2013-06-19T00:00:00-04:00" }, "priceType": "usage",
 * "unitOfMeasure": "second", "price": { "amount": "12", "currency": "$" },
 * "recurringChargePeriod": "" } ], "bundledProductOfferings":
 * {"productOffering": [ "id":
 * “http://serverlocation:port/catalogManagement/productOffering/15", "name":
 * "Offering 1", "description": "description 1" ]} }}
 *
 * @author pierregauthier
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductOffering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    String name;
    String description;
    Boolean isBundle;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column =
                @Column(name = "validForStart")),
        @AttributeOverride(name = "endDateTime", column =
                @Column(name = "validForEnd"))
    })
    TimeRange validFor;
    
    @ElementCollection
    @CollectionTable(
            name = "PO_PRODUCT_CATEGORY_REF",
            joinColumns =
            @JoinColumn(name = "PRODUCT_OFFERING_ID"))
    List<RefInfo> productCategories;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column =
                @Column(name = "productSpecName")),
        @AttributeOverride(name = "description", column =
                @Column(name = "productSpecDesc")),
        @AttributeOverride(name = "id", column =
                @Column(name = "productSpecId"))
    })
    RefInfo productSpecification;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column =
                @Column(name = "slaSpecName")),
        @AttributeOverride(name = "description", column =
                @Column(name = "slaSpecDesc")),
        @AttributeOverride(name = "id", column =
                @Column(name = "slaId"))
    })
    private RefInfo sla;

    @ElementCollection
    @CollectionTable(
            name = "PO_PRODUCT_OFFERING_PRICE",
            joinColumns =
            @JoinColumn(name = "PRODUCT_OFFERING_ID"))
    List<ProductOfferingPrice> productOfferingPrices;

    @ElementCollection
    @CollectionTable(
            name = "PO_BUNDLED_PO_REF",
            joinColumns =
            @JoinColumn(name = "PRODUCT_OFFERING_ID"))
    List<RefInfo> bundledProductOfferings;

    public String getId() {
        return id;
    }

    public List<RefInfo> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<RefInfo> productCategories) {
        this.productCategories = productCategories;
    }

    public List<ProductOfferingPrice> getProductOfferingPrices() {
        return productOfferingPrices;
    }

    public void setProductOfferingPrices(List<ProductOfferingPrice> productOfferingPrices) {
        this.productOfferingPrices = productOfferingPrices;
    }

    public List<RefInfo> getBundledProductOfferings() {
        return bundledProductOfferings;
    }

    public void setBundledProductOfferings(List<RefInfo> bundledProductOfferings) {
        this.bundledProductOfferings = bundledProductOfferings;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void setProductSpecification(RefInfo productSpecification) {
        this.productSpecification = productSpecification;
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

    public RefInfo getProductSpecification() {
        return productSpecification;
    }

    
    /**
     * @return the sla
     */
    public RefInfo getSla() {
        return sla;
    }

    /**
     * @param sla the sla to set
     */
    public void setSla(RefInfo sla) {
        this.sla = sla;
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
        if (this.sla != other.sla && (this.sla == null || !this.sla.equals(other.sla))) {
            return false;
        }
        if (this.productSpecification != other.productSpecification && (this.productSpecification == null || !this.productSpecification.equals(other.productSpecification))) {
            return false;
        }
        if (this.productCategories != other.productCategories && (this.productCategories == null || !this.productCategories.equals(other.productCategories))) {
            return false;
        }
        if (this.productOfferingPrices != other.productOfferingPrices && (this.productOfferingPrices == null || !this.productOfferingPrices.equals(other.productOfferingPrices))) {
            return false;
        }
        if (this.bundledProductOfferings != other.bundledProductOfferings && (this.bundledProductOfferings == null || !this.bundledProductOfferings.equals(other.bundledProductOfferings))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "ProductOffering{" + "id=" + id + ", name=" + name + ", description=" + description + ", isBundle=" + isBundle + ", validFor=" + validFor + ", productCategories=" + productCategories + ", productSpecification=" + productSpecification+ ", sla=" + sla + ", productOfferingPrices=" + productOfferingPrices + ", bundledProductOffering=" + bundledProductOfferings + '}';
    }

}
