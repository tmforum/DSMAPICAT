/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *{
    "productOfferingPrice": {
        "name": "MonthlyPrice",
        "name": "Monthly Price",
        "validFor": {"startDateTime":"2013-04-19T16:42:23-04:00",
                "endDateTime":"2013-06-19T00:00:00-04:00"
                },
        "priceType": "recurring",
        "unitOfMeasure": "",
        "price": {
            “amount” : "12",
            “currency” : “$”
        },
        "recurringChargePeriod": "month"
    }
 * @author pierregauthier
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class ProductOfferingPrice implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(ProductOfferingPrice.class.getName());
    
    
    String name;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column =
                @Column(name = "validForStart")),
        @AttributeOverride(name = "endDateTime", column =
                @Column(name = "validForEnd"))
    })
    TimeRange validFor;
    
    String priceType;
    
    String unitOfMeasure;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column =
                @Column(name = "priceAmount")),
        @AttributeOverride(name = "currency", column =
                @Column(name = "priceCurrency"))
    })
    Price price;
    
    String recurringChargePeriod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getRecurringChargePeriod() {
        return recurringChargePeriod;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 59 * hash + (this.priceType != null ? this.priceType.hashCode() : 0);
        hash = 59 * hash + (this.unitOfMeasure != null ? this.unitOfMeasure.hashCode() : 0);
        hash = 59 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 59 * hash + (this.recurringChargePeriod != null ? this.recurringChargePeriod.hashCode() : 0);
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
        final ProductOfferingPrice other = (ProductOfferingPrice) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.validFor != other.validFor && (this.validFor == null || !this.validFor.equals(other.validFor))) {
            return false;
        }
        if ((this.priceType == null) ? (other.priceType != null) : !this.priceType.equals(other.priceType)) {
            return false;
        }
        if ((this.unitOfMeasure == null) ? (other.unitOfMeasure != null) : !this.unitOfMeasure.equals(other.unitOfMeasure)) {
            return false;
        }
        if (this.price != other.price && (this.price == null || !this.price.equals(other.price))) {
            return false;
        }
        if ((this.recurringChargePeriod == null) ? (other.recurringChargePeriod != null) : !this.recurringChargePeriod.equals(other.recurringChargePeriod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductOfferingPrice{" + "name=" + name + ", validFor=" + validFor + ", priceType=" + priceType + ", unitOfMeasure=" + unitOfMeasure + ", price=" + price + ", recurringChargePeriod=" + recurringChargePeriod + '}';
    }
    
    
    
    
    
    
    
    
    
}
